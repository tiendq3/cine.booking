package com.tiendq.cinebooking.security.jwt;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(httpServletRequest);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                UsernamePasswordAuthenticationToken authenticationToken = jwtProvider.getAuthentication(httpServletRequest);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (JwtException | IllegalArgumentException e) {
            // Nếu token không hợp lệ, trả về lỗi 401 (Unauthorized)
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
