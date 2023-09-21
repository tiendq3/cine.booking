package com.tiendq.cinebooking.security;

import com.tiendq.cinebooking.security.jwt.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/**/management/**").hasAuthority(AuthorityConstants.ADMIN)
                .antMatchers("/chats-admin").permitAll()
                .antMatchers("/**/register").permitAll()
                .antMatchers("/**/login").permitAll()
                .antMatchers("/**/send-otp").permitAll()
                .antMatchers("/**/orders").permitAll()
                .antMatchers("/**/products").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/chats").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/**/favorite-movies").permitAll()
                .antMatchers("/**/rooms/**").permitAll()
//                .antMatchers("/**/rooms/**").hasAnyAuthority(AuthorityConstants.ADMIN,AuthorityConstants.USER)
                .antMatchers("/**/seats/**").permitAll()
                .antMatchers("/**/ws/**").permitAll()
                .antMatchers("/**/films-showing/**").permitAll()
                .antMatchers("/**/films/**/").permitAll()
                .antMatchers("/**/tickets/**/").permitAll()
                .antMatchers("/**/showtimes/**/").permitAll()
                .antMatchers("/**/categories/**/").permitAll()
                .antMatchers("/**/users/**/").hasAnyAuthority(AuthorityConstants.ADMIN,AuthorityConstants.USER)
                .antMatchers("/**/tickets/user-current").hasAnyAuthority(AuthorityConstants.ADMIN,AuthorityConstants.USER)
                .anyRequest().authenticated()
                .and()
//                .httpBasic()
//                .and()
//                .formLogin()
//                .failureUrl("/login")
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/**/un-authorities")
//                .and()
                .logout();
        return http.build();
    }
}