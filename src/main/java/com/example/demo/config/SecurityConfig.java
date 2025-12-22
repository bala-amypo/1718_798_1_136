package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // ❌ Disable default login page
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // 🚨 DO NOT define exceptionHandling (this is key!)
            // Let Spring Boot handle errors → Whitelabel

            .authorizeHttpRequests(auth -> auth
                // 🔑 allow error controller
                .requestMatchers("/error").permitAll()

                // allow swagger
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()

                // everything else secured
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
