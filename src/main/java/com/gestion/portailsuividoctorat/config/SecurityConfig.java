package com.gestion.portailsuividoctorat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())          // allow POST forms without CSRF token
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()           // session checks are done manually in controllers
                )
                .formLogin(form -> form.disable())      // disable Spring Security's own login
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}