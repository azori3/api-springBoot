package com.example.application.Config;

import com.example.application.User.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.example.application.User.Permission.*;
import static com.example.application.User.Role.ADMIN;
import static com.example.application.User.Role.Manager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
//        XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
//        // set the name of the attribute the CsrfToken will be populated on
//        delegate.setCsrfRequestAttributeName("_csrf");
//        // Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
//        // default implementation of resolveCsrfTokenValue() from CsrfTokenRequestHandler
//        CsrfTokenRequestHandler requestHandler = delegate::handle;
             http.cors().and()
                .csrf().disable()
//                  .csrf((csrf) -> csrf
//                           .csrfTokenRepository(tokenRepository)
//                             .csrfTokenRequestHandler(requestHandler)
//              )
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .requestMatchers("api/v1/management/**").hasAnyRole(ADMIN.name(), Manager.name())
                .requestMatchers(HttpMethod.GET, "api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(),MANAGER_READ.name())
                .requestMatchers(HttpMethod.DELETE, "api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(),MANAGER_DELETE.name())
                .requestMatchers(HttpMethod.POST, "api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(),MANAGER_CREATE.name())
                .requestMatchers(HttpMethod.PUT, "api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(),MANAGER_UPDATE.name())
                .requestMatchers( "api/v1/admin/**").hasRole(ADMIN.name())
                .requestMatchers(HttpMethod.GET, "api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                .requestMatchers(HttpMethod.DELETE, "api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
                .requestMatchers(HttpMethod.POST, "api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                .requestMatchers(HttpMethod.PUT, "api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
           //

        return http.build();
    }




}