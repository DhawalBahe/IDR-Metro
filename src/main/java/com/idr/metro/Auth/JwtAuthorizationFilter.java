package com.idr.metro.Auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.idr.metro.entity.User;
import com.idr.metro.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserRepository userRepository, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();

        logger.debug("Entering JwtAuthorizationFilter :: doFilterInternal method...");
        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }
            logger.info("token: {}", accessToken);
            Claims claims = jwtUtil.resolveClaims(request);

            if (claims != null && jwtUtil.validateClaims(claims)) {
                String email = claims.getSubject();
                logger.info("email: {}", email);

                // Fetch user from database based on email using UserRepository
                Optional<User> optionalUser = userRepository.findByEmail(email);

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    // Extract roles from user
                    List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

                    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.error("User not found for email: {}", email);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                }
            }

        } catch (Exception e) {
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            logger.error("An error occurred in JwtAuthorizationFilter::doFilterInternal method", e);
            mapper.writeValue(response.getWriter(), errorDetails);
            return; // Exit filter chain on error
        }
        filterChain.doFilter(request, response);
    }
}
