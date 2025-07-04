package com.gabrielle_santiago.agenda.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, Filter securityFilter) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/register/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register/patient").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register/employee").permitAll()
                        .requestMatchers(HttpMethod.GET, "/calendar/authorize").permitAll()
                        .requestMatchers(HttpMethod.POST, "/calendar/create").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/consultations/available-slots").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/consultations/create").permitAll()
                        .requestMatchers("/websocket/**", "/topic/**", "/app/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
