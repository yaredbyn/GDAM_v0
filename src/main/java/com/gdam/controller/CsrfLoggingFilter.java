//package com.gdam.controller;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CsrfLoggingFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws IOException, jakarta.servlet.ServletException {
//        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        if (csrfToken != null) {
//            System.out.println("CSRF Token (Filter): " + csrfToken.getToken());
//        } else {
//            System.out.println("CSRF Token not found in Filter!");
//        }
//        filterChain.doFilter(request, response);
//    }
//}
//
