package com.tcc.api.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/auth/login",
            "/auth/signup",
            "/auth/validation-email-signup",
            "/swagger-ui.html",
            "/favicon.ico"
    };
    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;


    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = new String[]{

            "/users/validation-email-update",
            "/users/me",
            "/users/validation-email-delete-user",
            "/users/me",
            "/users",
    };

    public static final String [] ENDPOINTS_CUSTOMER = {

    };

    public static final String [] ENDPOINTS_STORE = {

    };
    public static final String [] ENDPOINTS_ADMIN = {
            "/admin/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                      a -> a
                              .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                              .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                              .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
                              .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("SURDO")
                              .requestMatchers(ENDPOINTS_STORE).hasRole("INTERPRETE")
                              .anyRequest().permitAll()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return httpSecurity.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
