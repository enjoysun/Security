package com.myou.service.security.Server.Config;

import com.myou.service.security.Common.Jwt.JwtAuthenticationTokenFilter;
import com.myou.service.security.Common.Service.Impl.JwtUserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/*
 * @Time    : 2019/9/19 5:14 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : WebSecurityConfiguration.java
 * @Software: IntelliJ IDEA
 */

/**
 * 该类模拟一个用户中心类,接入将要认证的用户(库内角色用户)，该模块认证成功，认证模块类即发放授权码
 */
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
        web.ignoring()
                .mvcMatchers("/rbac/auth/login")
                .mvcMatchers("/rbac/auth/refresh")
                .mvcMatchers("/login")
                .mvcMatchers("/error")
                .mvcMatchers("/oauth/error")
                .mvcMatchers("/oauth_approval")
                .mvcMatchers("/oauth/authorize")
                .mvcMatchers("/oauth/confirm_access")
                .mvcMatchers("/oauth/token")
                .mvcMatchers("/oauth/confirm_access")
                .mvcMatchers("/oauth/**");
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
                // 自定义权限加载验证
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
                .and().formLogin().loginPage("/login")
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

    // 定制验证范围来源
//    @Bean
//    public AccessDecisionManager accessDecisionManager() {
//        List<AccessDecisionVoter<?>> voters = Arrays.asList(
//                new WebExpressionVoter(),
//                new RoleBaseRoute(),
//                new AuthenticatedVoter()
//        );
//        return new UnanimousBased(voters);
//    }

    @Bean
    public FilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetaDataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        return new CustomFilterInvocationSecurityMetaDataSource(filterInvocationSecurityMetadataSource);
    }

    @Bean
    public AccessDecisionManager customAccessDecisionManager() {
        return new CustomAccessDecisionManager();
    }
}
