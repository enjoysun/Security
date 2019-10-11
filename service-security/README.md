#### spring-security项目介绍  

> 该模块参照oauth协议的认证模块,登录信息编码至jwt中,实现动态资源验证  

> 控制路由模块类:WebSecurityConfigurerAdapter实现类中   
UsernamePasswordAuthenticationFilter->AbstractUserDetailsAuthenticationProvider->SecurityContextHolder.getContext().setAuthentication链路 


> JwtUtil.class:token包装工具类  
JwtAuthentication.class:截获token、认证获取权限过滤器(由httpSecurity添加该过滤器至spring security链路中并生效)  
JwtUserDetail:增加userDetails定制user信息实体

> Token：   
登录:/rbac/auth/login  
刷新:/rbac/auth/refresh  
注册:/rbac/auth/register  
余下路由皆需要接入认证  

#### 认证和授权模型设计  
![image](https://github.com/enjoysun/Security/blob/master/service-security/src/main/resources/images/doc/tb-er.png)  

###### 认证模块设计   

> 认证模块设计主要分为三大块:  
1.用户模块(用户表、用户组等扩展)  
2.角色模块(角色表、角色组、用户角色映射表等扩展)  
3.资源模块也做菜单表(权限表(可细致描述一个restApi接口url和请求方法鉴权)、角色权限映射表)  

###### 授权模块设计  

> 授权模块设计借鉴官方提供模型即本项目资源目录下security.sql文件。其中oauth_client_details表记录了授权所需app_id、secret、redirect_url  


#### 用户认证模块

> 用户认证模块实现用户账户密码认证、token刷新两个主要api为例    
而认证中心核心三个概念:user、role、permission(即用户输入登录信息加载角色信息验证权限是否允许访问)  
注:本例采用jwt进行信息传输

###### 1.用户角色加载至系统  

> 实现UserDetailsService接口进行角色信息加载至系统中 
 
> 该接口主要实现类loadUserByUsername(String username):
该类根据用户名进行数据库查询出该用户所有角色进行UserDetails实现类实例化  
因为该案例使用jwt增强了token所以可参照com.myou.service.security.Common.Jwt.JwtUserDetail实现类，该类对UserDetails进行jwt增强  
注明:com.myou.service.security.Common.Jwt.JwtUtil是产生jwtToken的工具类该类包含token产生、验证、刷新、过期时间设置等操作

参考列表:
com.myou.service.security.Common.Service.Impl.JwtUserDetailImpl  

```java
@Service
public class JwtUserDetailImpl implements UserDetailsService {
    @Autowired
    private TbUserService userService;

    @Autowired
    private TbPermissionService permissionService;

    @Autowired
    private TbRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (StringUtils.isBlank(s))
            throw new UsernameNotFoundException("用户名为空");
        TbUser tbUser = userService.findByUserName(s);
        if (tbUser == null)
            throw new UsernameNotFoundException("不存在用户");
        List<TbRole> tbRoles = roleService.RolesById(tbUser.getId());
        // 构造角色列表而非权限路由路径
        // 构造userDetail实例:user+permission
        HashSet<GrantedAuthority> hashSet = new HashSet<>();
        tbRoles.forEach(item -> {
            hashSet.add(
//                    new SimpleGrantedAuthority(item.getEnname())
                    new CustomGrantedAuthority(item, permissionService.selectByRoleId(item.getId()))
            );
        });
        JwtUserDetail jwtUserDetail = new JwtUserDetail(
                tbUser.getUsername(),
                tbUser.getPassword(),
                hashSet,
                UserState.NORMAL.getState()
        );
        return jwtUserDetail;
    }
}
```  

###### 2.截获请求token并验证(该案例使用jwtToken，验证过程即接祖JwtUtil无需关联数据库或者缓存，下文有普通token对接存储验证步骤)  

> 该步骤主要对每一个请求进行Authorization的Token截取并验证,验证成功后产生UsernamePasswordAuthenticationToken对象并交由SecurityContextHolder上下文  

参考类:
com.myou.service.security.Common.Jwt.JwtAuthenticationTokenFilter  

```java
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailImpl jwtUserDetail;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 拦截请求token验证并操作
        String requestHeader = httpServletRequest.getHeader(jwtUtil.getTokenHeader());
        log.info("token:{}", requestHeader);
        if (StringUtils.isNotEmpty(requestHeader) && StringUtils.isNotBlank(requestHeader)) {
            requestHeader = requestHeader.replaceAll(jwtUtil.getTokenHead(), "");
        }
        String username = jwtUtil.getUsernameFromToken(requestHeader);
        if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetail.loadUserByUsername(username);
            // 验证token和客户端匹配与否
            if (jwtUtil.validateToken(requestHeader, userDetails)) {
                // 构造信息绑定到后续request(重点参照)
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
```

###### 3.配置认证中心  

> security分为两部分:WebSecurityConfigurerAdapter和AuthorizationServerConfigurerAdapter前者为认证类作用于本系统或子系统用户认证和鉴权后者为授权类
作用于第三方服务调用授权(主要实现授权码模式进行第三方授权)。该模块主要实现前者，将token截取、密码加密方式、路由配置、等进行集中配置到过滤器  

<h4>参考本项目中认证中心配置类如下:</h4>  
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    // security密码加密处理类
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtUserDetailImpl userDetailsService;

    // 定制jwtToken处理过滤器
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 过滤配置。该出配置不会被security鉴权的路由
        web.ignoring().mvcMatchers("/rbac/auth/login")
                .mvcMatchers("/rbac/auth/refresh");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                // 取消spring的session机制
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 允许所有options动作可访问
//                .authorizeRequests().accessDecisionManager(accessDecisionManager()).antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 暴露关于token操作接口
                .antMatchers("/auth/**").permitAll()
                // 页面按照权限进行访问限制
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/test/**").permitAll()
                // 尚未被以上规则匹配的URL都需要进行身份验证配置
                .anyRequest().authenticated()
                .and().headers().cacheControl();
        // 注入jwt过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // 跨域配置(WebSecurityHttp也需要支持cors().and())
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.addAllowedOrigin("*");
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", cors);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
```  

###### 4.进行login、refresh api编写  

参考: 
com.myou.service.security.Service.Impl.UserServiceImpl  

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbPermissionService permissionService;

    @Autowired
    private TbUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserInfo findUserInfo(String name) throws UserNotExitException {
        TbUser user = userService.findByUserName(name);
        if (null == user)
            throw new UserNotExitException("用户不存在");
        List<TbPermission> permissions = permissionService.selectByUserId(user.getId());
        return UserInfo.builder().permission(permissions).user(user).build();
    }

    @Override
    public String login(TbUser user) throws AuthenticationException {
        /**
        * 登录接口也处于tokenFilter拦截范围，由于初次登录Authorization中token为null所以拦截器跳过验证块转向  
        * 下一链，所以构建UsernamePasswordAuthenticationToken和传入SecurityContextHolder需要在不含token的
        * 请求中手动进行操作
        * */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtUtil.generateToken(userDetails);
    }

    @Override
    public TbUser register(TbUser user) throws Exception {
        TbUser tbUser = userService.findByUserName(user.getUsername());
        if (null != tbUser)
            throw new UserExitsException(String.format("用户名%s已存在", user.getUsername()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        TbUser insertTbUser = userService.insertTbUser(user);
        return insertTbUser;
    }

    @Override
    public String refresh_token(String old_token) {
        return !jwtUtil.isTokenExpired(old_token) ? jwtUtil.refreshToken(old_token) : null;
    }
}
```

#### 动态权限验证设计  

> 执行完上诉4个步骤之后，系统以及能进行权限过滤，但仅限于配置完善的权限，不适用与微服务中需要动态配置的权限，为了将该rbac模块构建成认证授权中心服务，还需要对认证服务进行动态权限鉴权  
而动态验证过程可扩展api的url和method等验证。步骤:  
1.加载所有的角色和权限对应数据关系至SecurityMetaDataSource  
2.截取请求的url进行和SecurityMetaDataSource中权限列表比对获取角色代码  
3.在AccessDecisionManager中对该角色代码进行决策(即加载用户所有角色代码进行对比)  

###### 1.加载所有角色和权限关系至SecurityMetaDataSource，实现FilterInvocationSecurityMetadataSource进行权限-角色数据组装  

参考:
com.myou.service.security.Server.Config.CustomFilterInvocationSecurityMetaDataSource  
```java
/**
 * 动态加载权限并验证
 * SecurityMetadataSource:访问权限资源中心(加载所有权限至系统)
 * 权限资源:
 * url:role（一个url对应多个角色、一个角色对应多个url）
 */
public class CustomFilterInvocationSecurityMetaDataSource implements FilterInvocationSecurityMetadataSource {

    private FilterInvocationSecurityMetadataSource superMetadataSource;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private TbRoleService roleService;

    // 权限资源集合
    private Map<String, Collection<ConfigAttribute>> map = new HashMap<>();

    public CustomFilterInvocationSecurityMetaDataSource(FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource) {
        superMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        List<TbRole> roles = roleService.selectRolesAndPermission();
        /**
        * map结构分析:
        * key=url value=List<role>
        * key如果相同即value被覆盖，所以采用url为key，role列表为value    
        * */
        roles.forEach(item -> {
            item.getPermissions().forEach(per -> {
                map.put(per.getUrl(), new ArrayList<ConfigAttribute>(Arrays.asList(new SecurityConfig(item.getEnname()))));
            });
        });
        FilterInvocation invocation = (FilterInvocation) o;
        String url = invocation.getRequestUrl();
        String method = invocation.getRequest().getMethod();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
            if (antPathMatcher.match(entry.getKey(), url)) {
                return entry.getValue();
            }
        }
        /**
        * 当前角色匹配失败自动赋予LOGIN游客角色，若用户不含该角色则无权限访问
        * 若匹配失败则默认为游客权限
        * 该出可以进行白名单和黑名单设置
        * */
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
``` 

###### 2.拦截访问url获取可访问角色后进行角色决策，实现AccessDecisionManager进行决策  

参考:
com.myou.service.security.Server.Config.CustomAccessDecisionManager  

```java
/**
 * 在自定义的SecurityMetaDadaSource中加载了权限资源，在该模块进行资源决策
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        /**
        * authentication:用户信息含角色信息
        * o:转为FilterInvocation对象可获取web的request资源(url、method等)
        * collection:本次访问所需权限(即上一个步骤中产生的role角色)
        * */
        Iterator<ConfigAttribute> configAttributeIterator = collection.iterator();
        while (configAttributeIterator.hasNext()) {
            if (authentication == null) {
                throw new AccessDeniedException("用户信息加载失败，权限不足");
            }
            ConfigAttribute attribute = configAttributeIterator.next();
            String needCode = attribute.getAttribute();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (StringUtils.equals(authority.getAuthority(), needCode)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
```  


###### 3.将动态权限决策和验证配置到认证中心  

参考：
com.myou.service.security.Server.Config.WebSecurityConfiguration  
新增以下配置
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtUserDetailImpl userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 过滤配置
//        super.configure(web);
        web.ignoring().mvcMatchers("/rbac/auth/login")
                .mvcMatchers("/rbac/auth/refresh");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                // 取消spring的session机制
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 允许所有options动作可访问
//                .authorizeRequests().accessDecisionManager(accessDecisionManager()).antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .authorizeRequests()
                // **自定义权限加载验证**
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(customFilterInvocationSecurityMetaDataSource(o.getSecurityMetadataSource()));
                        o.setAccessDecisionManager(customAccessDecisionManager());
                        return o;
                    }
                })
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 暴露关于token操作接口
                .antMatchers("/auth/**").permitAll()
                // 页面按照权限进行访问限制
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/test/**").permitAll()
                // 尚未被以上规则匹配的URL都需要进行身份验证配置
                .anyRequest().authenticated()
                .and().headers().cacheControl();
        // 注入jwt过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // 跨域配置(WebSecurityHttp也需要支持cors().and())
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.addAllowedOrigin("*");
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", cors);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    // **新增**
    @Bean
    public FilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetaDataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        return new CustomFilterInvocationSecurityMetaDataSource(filterInvocationSecurityMetadataSource);
    }

    // **新增**
    @Bean
    public AccessDecisionManager customAccessDecisionManager() {
        return new CustomAccessDecisionManager();
    }
}
```

#### token接入和验证  

 

#### 授权设计