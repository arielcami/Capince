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
		authProvider.setUserDetailsService(customUserDetailsService); // Usa tu CustomUserDetailsService
		authProvider.setPasswordEncoder(passwordEncoder()); // Usa tu PasswordEncoder
		return authProvider;
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // Endpoint de autenticación (público)
                .requestMatchers("/authenticate").permitAll()

                // Ahora solo /api/producto es completamente pública
                .requestMatchers("/api/producto").permitAll()

                // Rutas que pueden ver ADMIN, GERENTE y MESERO (excluyendo RECEPCION)
                .requestMatchers("/api/pedido", "/api/detalle-pedido").hasAnyRole("ADMIN", "GERENTE", "MESERO")

                // Rutas que pueden ver solamente ADMIN y GERENTE
                .requestMatchers("/api/empleado", "/api/cliente").hasAnyRole("ADMIN", "GERENTE")

                // Cualquier otra solicitud requiere autenticación (y no está cubierta por las reglas anteriores)
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