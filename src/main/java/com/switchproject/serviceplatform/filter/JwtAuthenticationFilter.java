package com.switchproject.serviceplatform.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.switchproject.serviceplatform.service.CustomUserDetailsService;
import com.switchproject.serviceplatform.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
        @Autowired
        private JWTService jwtService;

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
        {
                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer "))
                {
                        filterChain.doFilter(request, response);
                        return;
                }

                String token = authHeader.substring(7);

                String username = jwtService.extractUsername(token);

                // logger.info("Authorization header: {}", authHeader);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
                {
                        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                        logger.info("Loaded user: {}", userDetails.getUsername());
                        logger.info("Authorities: {}", userDetails.getAuthorities());

                        if (jwtService.validateToken(token, userDetails))
                        {
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                SecurityContextHolder.getContext().setAuthentication(authentication);

                                logger.info("Authentication set: {}", authentication);
                                logger.info("Authenticated: {}",
                                        SecurityContextHolder.getContext()
                                                .getAuthentication().isAuthenticated());
                        }
                }

                filterChain.doFilter(request, response);
        }
}