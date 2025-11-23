package com.example.quadalearn.config;

import com.example.quadalearn.config.service.JwtService;
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
import java.util.Set;

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

                        .requestMatchers("/api/ai/**").permitAll()

                        .requestMatchers("/tests/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers("/api/survey/**").permitAll()

                        .requestMatchers("/api/reading-passages/**").permitAll()
                        .requestMatchers("/api/vocabularies/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/grammar-examples/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/knowledge/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/topic-types/**").permitAll()
                        // ==== Public GET Endpoint (phải để trước matcher tổng quát) ====
                        .requestMatchers(HttpMethod.GET, "/courses/top6").permitAll()
                        .requestMatchers(HttpMethod.GET, "/courses/level").permitAll()

                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/login/oauth2/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/courses/{id}/lessons").permitAll()
                        // GET /courses và /courses/{id} → USER & ADMIN được truy cập
                        .requestMatchers(HttpMethod.GET, "/courses/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/questions/test/1").permitAll()
                        .requestMatchers(HttpMethod.GET, "/courses/top6").permitAll() // cụ thể → public
//                        .requestMatchers(HttpMethod.GET, "/courses/**").hasAnyRole("USER", "ADMIN") // chung → cần role

                        // ==== GET yêu cầu xác thực (user/admin) ====
                        .requestMatchers(HttpMethod.GET, "/courses/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/lesson/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/grammar-topics/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/vocabulary", "/vocabulary/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER", "ADMIN")


                        // Protected ADMIN only
                        .requestMatchers("/rest/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/lesson").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/lesson/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/lesson/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/vocabulary").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/vocabulary/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vocabulary/**").hasRole("ADMIN")


                        // Tất cả request còn lại cần authenticated
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            var oauthUser = (org.springframework.security.oauth2.core.user.OAuth2User) authentication.getPrincipal();
                            String email = oauthUser.getAttribute("email");
                            String name = oauthUser.getAttribute("name");

                            // Tìm user trong DB
                            var user = userService.findByEmail(email);
                            if (user == null) {
                                user = new com.example.quadalearn.model.auth.User();
                                user.setEmail(email);
                                user.setName(name);
                                user.setPassword("GOOGLE_LOGIN");
                                user.setRoles(Set.of(new com.example.quadalearn.model.auth.Role(2L, "ROLE_USER")));
                                user = userService.add(user);
                            }

                            // Sinh JWT
                            String jwt = new JwtService().generateToken(user);

                            boolean needsCompletion = (user.getGoal() == null || user.getCurrentLevel() == null);

                            // Redirect về frontend (Next.js)
                            String redirectUrl = "http://localhost:3000/oauth2/callback?token=" + jwt
                                    + "&name=" + java.net.URLEncoder.encode(user.getName(), java.nio.charset.StandardCharsets.UTF_8)
                                    + "&email=" + java.net.URLEncoder.encode(user.getEmail(), java.nio.charset.StandardCharsets.UTF_8)
                                    + "&needsCompletion=" + needsCompletion;
                            response.sendRedirect(redirectUrl);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.sendRedirect("http://localhost:3000/authenticate/login?error=google");
                        })
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
