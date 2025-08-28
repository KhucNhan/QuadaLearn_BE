package com.example.quadalearn.config;

import com.example.quadalearn.rest.CustomAccessDeniedHandler;
import com.example.quadalearn.rest.JwtAuthenticationTokenFilter;
import com.example.quadalearn.rest.RestAuthenticationEntryPoint;
import com.example.quadalearn.service.UserService;
import com.example.quadalearn.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserServiceImpl userService;
    private final JwtAuthenticationTokenFilter jwtAuthenticationFilter;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserServiceImpl userService,
                          JwtAuthenticationTokenFilter jwtAuthenticationFilter,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Tắt CSRF vì dùng JWT
                .csrf(csrf -> csrf.disable())
                // CORS config
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Xử lý exception (401, 403)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                // Stateless session (không dùng session để lưu auth)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Phân quyền
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/rest/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rest/users").permitAll()
                        .anyRequest().authenticated()
                )
                // Cấu hình AuthenticationProvider
                .authenticationProvider(daoAuthenticationProvider())
                // Thêm JWT filter trước UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // UserService phải implements UserDetailsService
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Cho phép frontend React call API
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
