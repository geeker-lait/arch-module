//package org.arch.application.user.properties.security;
//
//import org.springframework.security.properties.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.properties.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// *
// */
//public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private final TokenProvider tokenProvider;
//
//    public TokenConfigurer(TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) {
//        TokenFilter customFilter = new TokenFilter(tokenProvider);
//        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//}
