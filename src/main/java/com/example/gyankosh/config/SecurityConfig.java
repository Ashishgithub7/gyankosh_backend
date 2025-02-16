package com.example.gyankosh.config;

import com.example.gyankosh.security.JwtAuthenticationEntryPoint;
import com.example.gyankosh.security.JwtAuthenticationFilter;
import com.example.gyankosh.service.CustomUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig extends WebMvcConfigurationSupport {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private final JwtAuthenticationFilter filter;
    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    public SecurityConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public AuthenticationProvider userAuthenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(userAuthenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/v1/user/register","/api/v1/user/login").permitAll()
//                        .anyRequest().authenticated()) if required paxi change garna
                        .anyRequest().permitAll())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(userAuthenticationProvider()).addFilterBefore( filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}

