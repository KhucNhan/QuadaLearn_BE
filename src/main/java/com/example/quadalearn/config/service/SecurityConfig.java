package com.example.quadalearn.config.service;

import com.example.quadalearn.rest.CustomAccessDeniedHandler;
import com.example.quadalearn.rest.JwtAuthenticationTokenFilter;
import com.example.quadalearn.rest.RestAuthenticationEntryPoint;
import com.example.quadalearn.service.impl.auth.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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

    private final UserService userService;
    private final JwtAuthenticationTokenFilter jwtAuthenticationFilter;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService,
                          JwtAuthenticationTokenFilter jwtAuthenticationFilter,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // ✅ dùng Customizer thay vì .cors().and()
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Cho phép login và tạo user
                        .requestMatchers(HttpMethod.POST, "/rest/users").permitAll()
                        .requestMatchers("/api/ai/**").permitAll() // 👉 cho phép gọi API AI không cần JWT
                        .requestMatchers("/tests/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()


                        // Cấu hình AuthenticationProvider
                        // Cho phép tất cả các endpoint survey mà không cần JWT
                        .requestMatchers("/api/survey/**").permitAll()
                        // Các endpoint khác bắt buộc phải authenticated
                        // GET /courses và /courses/{id} → USER & ADMIN được truy cập
                        .requestMatchers(HttpMethod.GET, "/courses/level").permitAll()
                        .requestMatchers(HttpMethod.GET, "/questions/test/1").permitAll()
                        .requestMatchers(HttpMethod.GET, "/courses/top6").permitAll() // cụ thể → public
                        .requestMatchers(HttpMethod.GET, "/courses/**").hasAnyRole("USER", "ADMIN") // chung → cần role


                        // POST, PUT, DELETE → chỉ ADMIN
                        .requestMatchers(HttpMethod.POST, "/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/courses/**").hasRole("ADMIN")


                        // LESSON CONTROLLER
                        .requestMatchers(HttpMethod.GET, "/lesson", "/lesson/**").hasAnyRole("USER", "ADMIN") // Xem bài học
                        .requestMatchers(HttpMethod.POST, "/lesson").hasRole("ADMIN") // Tạo mới
                        .requestMatchers(HttpMethod.PUT, "/lesson/**").hasRole("ADMIN") // Cập nhật
                        .requestMatchers(HttpMethod.DELETE, "/lesson/**").hasRole("ADMIN") // Xóa


                        // VOCABULARY CONTROLLER
                        .requestMatchers(HttpMethod.GET, "/vocabulary", "/vocabulary/**").hasAnyRole("USER", "ADMIN") // USER & ADMIN
                        .requestMatchers(HttpMethod.POST, "/vocabulary").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/vocabulary/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vocabulary/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false);
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
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // frontend React
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
