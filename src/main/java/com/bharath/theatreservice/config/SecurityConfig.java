package com.bharath.theatreservice.config;

import com.bharath.theatreservice.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Theatre endpoints
                        .requestMatchers(HttpMethod.GET, "/api/theatre", "/api/theatre/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/theatre", "/api/theatre/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/theatre", "/api/theatre/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/theatre", "/api/theatre/**").hasRole("ADMIN")

                        // Screen endpoints
                        .requestMatchers(HttpMethod.GET, "/api/screen", "/api/screen/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/screen", "/api/screen/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/screen", "/api/screen/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/screen", "/api/screen/**").hasRole("ADMIN")

                        // Seat endpoints
                        .requestMatchers(HttpMethod.GET, "/api/seat", "/api/seat/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/seat", "/api/seat/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/seat", "/api/seat/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/seat", "/api/seat/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
