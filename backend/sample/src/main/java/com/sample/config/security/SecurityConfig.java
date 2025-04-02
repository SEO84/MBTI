package com.sample.config.security;

import com.sample.config.security.token.CustomOncePerRequestFilter;
import com.sample.service.auth.CustomDefaultOAuth2UserService;
import com.sample.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomDefaultOAuth2UserService customOAuth2UserService;
    private final CustomOncePerRequestFilter customOncePerRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                // 공개 엔드포인트 설정 (로그인, 회원가입, OAuth2 관련 등)
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(
                                "/",
                                "/error",
                                "/login",
                                "/signup",
                                "/auth/**",
                                "/oauth2/**",
                                "/favicon.ico",
                                "/**/*.png",
                                "/**/*.gif",
                                "/**/*.svg",
                                "/**/*.jpg",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js"
                        )
                        .permitAll()
                        .anyRequest().authenticated()
                )
        // 필요한 경우 OAuth2 Login 설정 (주석 처리되어 있음)

          .oauth2Login(oauth2 -> oauth2
              .authorizationEndpoint(ce -> ce.baseUri("/oauth2/authorize"))
              .redirectionEndpoint(re -> re.baseUri("/oauth2/callback/*"))
              .userInfoEndpoint(ui -> ui.userService(customOAuth2UserService))
          )

        ;

        http.addFilterBefore(customOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
