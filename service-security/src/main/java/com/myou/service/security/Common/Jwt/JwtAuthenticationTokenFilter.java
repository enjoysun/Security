package com.myou.service.security.Common.Jwt;

import com.myou.service.security.Common.Service.Impl.JwtUserDetailImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Time    : 2019/9/26 5:33 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : JwtAuthenticationTokenFilter.java
 * @Software: IntelliJ IDEA
 */

/**
 * 构建JWT过滤器
 */
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
