package com.myprojects.realtimekotsystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. If header doesn't start with 'Bearer ', skip this filter and let request proceed
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Extract raw token from string

        try {
            userEmail = jwtUtils.extractEmail(jwt);

            // 2. If token contains email and user is not already authenticated in this thread context
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                if (jwtUtils.isTokenValid(jwt, userEmail)) {
                    String role = jwtUtils.extractClaim(jwt, claims -> claims.get("role", String.class));
                    Long restaurantId = jwtUtils.extractClaim(jwt, claims -> claims.get("restaurantId", Long.class));

                    // Map claims to custom authentication container
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                    // Create an Authentication object containing user info and tenant variables
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userEmail, // principal
                            null,
                            List.of(authority)
                    );

                    // Crucial step: attach restaurantId request-scoped attribute to extract inside endpoints easily
                    request.setAttribute("restaurantId", restaurantId);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 3. Authenticate the user for the lifetime of this request execution
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token parsing failed (expired, manipulated signature, etc.)
            logger.error("Cannot set user authentication: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
