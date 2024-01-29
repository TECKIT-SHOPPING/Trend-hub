package com.trendhub.trendhub.global.config.security;

import com.trendhub.trendhub.global.service.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(
                        form ->
                                form
                                        .loginPage("/members/login")
                                        .successHandler(((request, response, authentication) -> response.sendRedirect("/")))
                                        .usernameParameter("loginId")
                                        .failureUrl("/members/login/error")
                )
                .headers(
                        headers ->
                                headers.frameOptions(
                                        frameOptions ->
                                                frameOptions.sameOrigin()
                                )
                )
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/**").permitAll()
                )
                .exceptionHandling(
                        except ->
                                except.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                );


        return http.build();

    }

}