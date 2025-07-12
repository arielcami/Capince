package pe.com.capince.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFiltroAutenticacion jwtFiltroAutenticacion;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          JwtFiltroAutenticacion jwtFiltroAutenticacion) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtFiltroAutenticacion = jwtFiltroAutenticacion;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @SuppressWarnings("deprecation")
	@Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize

                // === RUTAS PÚBLICAS ===
                .requestMatchers("/authenticate").permitAll()
                .requestMatchers("/api/producto").permitAll()

                // === SOLO ADMIN ===
                .requestMatchers(
                    "/api/sexo",
                    "/api/distrito",
                    "/api/rol"
                ).hasRole("ADMIN")

                // === ADMIN y GERENTE ===
                .requestMatchers(
                    "/api/empleado",
                    "/api/tipo-documento",
                    "/api/tipo-producto"
                ).hasAnyRole("ADMIN", "GERENTE")

                // === ADMIN, GERENTE y MESERO ===
                .requestMatchers(
                    "/api/pedido",
                    "/api/detalle-pedido"
                ).hasAnyRole("ADMIN", "GERENTE", "MESERO")

                // === RECEPCION y superiores ===
                .requestMatchers("/api/cliente").hasAnyRole("RECEPCION", "GERENTE", "ADMIN")

                // === CUALQUIER OTRA RUTA requiere autenticación ===
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtFiltroAutenticacion, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
