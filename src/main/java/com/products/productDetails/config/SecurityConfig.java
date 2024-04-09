package com.products.productDetails.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails ben = User.builder()
                .username("ben")
                .password("{noop}test123")
                .roles("USER")
                .build();

        UserDetails gwen = User.builder()
                .username("gwen")
                .password("{noop}test123")
                .roles("USER","MANAGER")
                .build();

        UserDetails max = User.builder()
                .username("max")
                .password("{noop}test123")
                .roles("USER","MANAGER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(ben,gwen,max);
    }

    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests->
                        requests
                                .requestMatchers(AUTH_WHITE_LIST).permitAll()
                                .requestMatchers(HttpMethod.GET,"api/product-details/v1/products").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"api/product-details/v1/products/**").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"api/product-details/v1/stock-details/**").hasRole("USER")
                                .requestMatchers(HttpMethod.POST,"api/product-details/v1/products").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT,"api/product-details/v1/products/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE,"api/product-details/v1/products/**").hasRole("ADMIN")
                );

        //use http basic auth
        http.httpBasic(Customizer.withDefaults());

        //disable basic csrf (Cross Site Request Forgrey)
        http.csrf(csrf->csrf.disable());

        return http.build();

    }
}
