package com.medcon.auth.security;

import com.medcon.auth.service.JwtService;
import com.medcon.exception.UnAuthorizedException;
import com.medcon.shared.constants.AppConstants;
import com.medcon.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter is a filter that checks for JWT tokens in the Authorization header of incoming requests.
 * If a valid token is found, it authenticates the user and sets the security context.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String jwt = extractJwtFromRequest(request);
            if (jwt == null) {
                throw new UnAuthorizedException("JWT token is missing or invalid");
            }

            // If authentication context is already set, skip processing
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                logger.debug("Authentication context already set, skipping JWT processing");
                filterChain.doFilter(request, response);
                return;
            }

            // Process the JWT token and set the authentication context
            processJwtToken(jwt, request);

        } catch (ExpiredJwtException e) {
            logger.warn("JWT token has expired: {}", e.getMessage());
            handleAuthenticationError(response, "Token expired");
            return;
        } catch (MalformedJwtException e) {
            logger.warn("Invalid JWT token format: {}", e.getMessage());
            handleAuthenticationError(response, "Invalid token format");
            return;
        } catch (UnAuthorizedException e) {
            logger.warn("Unauthorized access: {}", e.getMessage());
            handleAuthenticationError(response, e.getMessage());
            return;
        } catch (Exception e) {
            logger.error("Error processing JWT token: ", e);
            handleAuthenticationError(response, "Authentication error");
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts JWT token from the Authorization header.
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX_LENGTH);
        }

        return null;
    }

    /**
     * Handles authentication errors by setting appropriate response status and message.
     */
    private void handleAuthenticationError(HttpServletResponse response, String message) throws IOException {
        // Clear any existing response content
        response.resetBuffer();

        // Set response headers
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", AppConstants.FRONTEND_URL);

        // Create JSON response
        String jsonResponse = String.format(
                "{\"error\": \"Authentication failed\", \"message\": \"%s\", \"timestamp\": %d}",
                message.replace("\"", "\\\""), // Escape quotes to prevent JSON issues
                System.currentTimeMillis()
        );

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    /**
     * Processes the JWT token and sets the authentication context.
     */
    private void processJwtToken(String jwt, HttpServletRequest request) {
        String userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElse(null);
            if (user != null && jwtService.isTokenValid(jwt, user)) {
                var authorities = user.getAuthorities();
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, authorities);
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.warn("User not found or token is invalid: {}", userEmail);
                throw new UnAuthorizedException("User not found or token is invalid for email");
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        var path = request.getServletPath();

        return path.startsWith("/api/auth") || path.startsWith("/api/health");
    }
}
