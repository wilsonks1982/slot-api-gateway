package org.wilsonks.slotapigateway.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // Disable CSRF - CSRF protects browser sessions.
                .csrf(AbstractHttpConfigurer::disable)

                // REST API → No Session - Normally Spring Security creates JSESSIONID and stores users in memory.We don't want that.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // URL Authorization
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/jwt-test",
                                "/api/employees/login",
                                "/api/players/session",
                                "/actuator/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                        )
                )
                // HTTP Basic disabled - Otherwise clients can send Authorization:Basic header to authenticate.
                .httpBasic(AbstractHttpConfigurer::disable)

                // Form Login disabled - Otherwise Spring shows HTML Login Page
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
