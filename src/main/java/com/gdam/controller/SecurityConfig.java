//package com.gdam.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.csrf.*;
//import org.springframework.web.cors.*;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private CsrfLoggingFilter csrfLoggingFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf().and()
//            .addFilterAfter(csrfLoggingFilter, CsrfFilter.class) // Add custom logging filter after Spring's CsrfFilter
//            .authorizeRequests()
//            .anyRequest().permitAll();
//        return http.build();
//    }
//}
