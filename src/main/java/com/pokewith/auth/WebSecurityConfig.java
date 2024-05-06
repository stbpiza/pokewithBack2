package com.pokewith.auth;

import com.pokewith.filter.RequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    // 암호화에 필요한 PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/favicon.ico"
                        ,"/error"
                        ,"/"
                        ,"/index"
                        ,"/mypage"
                        ,"/mypost"
                        ,"/join"
                        ,"/login"
                        ,"/register"
                        ,"/room"
                        ,"/static/**"
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/user/**").hasRole("USER"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/admin/**").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint()))
                .exceptionHandling(e -> e.accessDeniedHandler(accessDeniedHandler))
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 넣음
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new RequestFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }

}
