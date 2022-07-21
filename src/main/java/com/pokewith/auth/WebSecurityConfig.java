package com.pokewith.auth;

import com.pokewith.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    //private final CustomOAuth2UserService customOAuth2UserService;
    private final RedisService redisService;
    //private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    // 암호화에 필요한 PasswordEncoder를 Bean에 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

//    @Bean
//    public OAuth2AuthorizedClientService authorizedClientService() {
//        return new CustomOAuth2AuthorizedClientService(redisService);
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/user/**").hasAnyRole("USER")
                .antMatchers("/api/**").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-ui.html", "/webjars/**",
                        "/swagger/**", "/configuration/**", "/swagger-resources/**").permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .oauth2Login()

//                .authorizationEndpoint()
//                .baseUri("/oauth2/**")
//                .and()
                // 아직 안쓰는 것들
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)
//                .and()
                // 쓰는 것들
//                .redirectionEndpoint()
//                .baseUri("/login/oauth2/**")
//                .and()
//                .clientRegistrationRepository(clientRegistrationRepository)
                // oauth2
//                .authorizedClientService(authorizedClientService())
//                .successHandler(new CustomOAuth2SuccessHandler(redisService, authService))
                .and()
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 넣음
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}
