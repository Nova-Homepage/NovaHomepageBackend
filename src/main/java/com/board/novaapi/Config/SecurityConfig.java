package com.board.novaapi.Config;


import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.OAuth.Exceptions.RestAuthenticationEntryPoint;
import com.board.novaapi.OAuth.filter.TokenAuthenticationFilter;
import com.board.novaapi.OAuth.handler.OAuth2AuthenticationFailureHandler;
import com.board.novaapi.OAuth.handler.OAuth2AuthenticationSuccessHandler;
import com.board.novaapi.OAuth.handler.TokenAccessDeniedHandler;
import com.board.novaapi.OAuth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.board.novaapi.OAuth.service.CustomOAuth2UserService;
import com.board.novaapi.OAuth.service.CustomUserDetailsService;
import com.board.novaapi.OAuth.token.AuthTokenProvider;
import com.board.novaapi.repository.user.UserRefreshTokenRepository;
import com.board.novaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // auth manager
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //password Encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //tokenfilter
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    //OAuth authentication Success핸들러
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                userRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    //OAuth authentication FailureHandler

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    //Cor 설정
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }

    // 권한의 계단식 설정이 필요함


    //관리자 아이디가 필요한데, 관리자 아이디를 form 로그인으로 메모리에 저장해 두는 것이 어떤가 하는 생각으로 진행 no
    //관리자 권한을 갖은 토큰을 생성해 주는 것이 가장 best

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().logoutSuccessUrl("/oauth2/logout")
                .and()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/oauth2/**",
                        "/v2/api-docs",
                        "/info/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/swagger-ui/index.html",
                        "/swagger-ui.html/",
                        "/webjars/**",
                        "/swagger/**").permitAll()
                .antMatchers("/info/user").hasAnyAuthority(RoleType.USER.getCode(),RoleType.ADMIN.getCode())
                .antMatchers( "/auth/**").hasAnyAuthority(RoleType.GUEST.getCode(),RoleType.USER.getCode(),RoleType.ADMIN.getCode())
                .antMatchers( "/board/**","/comment/**","/file/**","/reply/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
}
