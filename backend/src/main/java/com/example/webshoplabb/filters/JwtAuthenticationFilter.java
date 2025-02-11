package com.example.webshoplabb.filters;

import com.example.webshoplabb.services.JwtService;
import com.example.webshoplabb.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Intercepts incoming requests, extracts and validates the JWT token,
 * and sets up the authentication context if the token is valid.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Always run the filter for every endpoint, no skipping unless specifically needed
        return false;  // Ensure all routes go through this filter
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter intercepting request...");

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // If no "Authorization" header or if the token is malformed, skip filtering
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from "Authorization" header
        jwt = authHeader.substring(7);
        System.out.println("JWT: " + jwt);

        // Extract user email from the JWT token
        userEmail = jwtService.extractUserName(jwt);
        System.out.println("✅ Extracted User from JWT: " + userEmail);

        // Validate the token and set authentication context
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Load user details from the database
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

                // If the token is valid, authenticate the user
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("User: " + userDetails);

                    // Set the authentication context
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            } catch (Exception e) {
                log.error("Error authenticating user from JWT", e);
            }
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
