package com.idr.metro.Auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final com.idr.metro.Auth.CustomUserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.userDetailsService = customUserDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.csrf(AbstractHttpConfigurer::disable)
//		.authorizeHttpRequests(auth -> auth.requestMatchers("/rest/auth/**").permitAll()
//						.requestMatchers("/HouseSearch/**").authenticated()
//						.requestMatchers("/propertyListing/**").authenticated()
//						.requestMatchers("/persons/**").authenticated()
//						.requestMatchers("/RelationShip/**").authenticated()
//						.requestMatchers("/social-account/**").authenticated()
//						.requestMatchers("/rest/agent/**").permitAll()
//						.requestMatchers("/deals/**").authenticated()
//						.anyRequest().authenticated())
//				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
//				.formLogin(Customizer.withDefaults()) // Enable form login
//				.oauth2Login(Customizer.withDefaults()); // Enable OAuth2 login
//
//		return httpSecurity.build();
//	}


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Allow access to these endpoints without authentication
                        .requestMatchers("/rest/auth/**").permitAll()
                        .requestMatchers("/rest/agent/**").permitAll()
                        // Endpoint permissions based on roles
                        .requestMatchers("/HouseSearch/**").authenticated()
                        .requestMatchers("/propertyListing/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .requestMatchers("/persons/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .requestMatchers("/RelationShip/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .requestMatchers("/social-account/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                        .requestMatchers("/deals/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN", "TEAM_LEAD")
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults()) // Enable form login
                .oauth2Login(Customizer.withDefaults()); // Enable OAuth2 login

        return httpSecurity.build();
    }

}
