package io.github.matheusvdlima.incidents.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    // Libera swagger (docs e UI), login e rotas públicas
    private static final String[] PUBLIC_MATCHERS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",      // importante para o redirect inicial do UI
            "/publico/**",
            "/login/**",
            "/error",                // evita 401/403 em páginas de erro
            "/favicon.ico"
    };

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint restAuthenticationEntryPoint;
    private final AccessDeniedHandler restAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        // Seu filtro de autenticação (login) em /login
        JWTAuthenticationFilter authFilter = new JWTAuthenticationFilter(jwtUtil, authManager);
        authFilter.setFilterProcessesUrl("/login");

        return http
                .cors(cors -> {}) // usa o bean corsConfigurationSource()
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Swagger + público
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()
                        // Pré-flight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Regras do seu domínio (mantidas)
                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                        // fallback
                        .anyRequest().authenticated()
                )
                // Coloca o filtro de autenticação exatamente na posição do UsernamePasswordAuthenticationFilter
                .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)
                // Filtro de autorização (lê o Bearer token em todas as requisições)
                .addFilterBefore(new JWTAuthorizationFilter(jwtUtil, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // Authorization precisa estar exposto para o front conseguir ler header de resposta se necessário
        configuration.addExposedHeader("Authorization");
        // (Opcional) Se quiser ser explícito:
        // configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
