package pe.com.capince.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

	@SuppressWarnings("unused")
	private final EmpleadoDetailsService empleadoDetailsService;

	public SecurityConfig(EmpleadoDetailsService empleadoDetailsService) {
		this.empleadoDetailsService = empleadoDetailsService;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		var csrfRequestHandler = new CsrfTokenRequestAttributeHandler();
		csrfRequestHandler.setCsrfRequestAttributeName("_csrf");

		/*
		// CSFR DESACTIVADO
		http
	    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	    .csrf(csrf -> csrf.disable()) // Desactiva CSRF
	    .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class) // Esto ya no es necesario si desactivas CSRF
	    .authorizeHttpRequests(auth -> auth
	        .requestMatchers("/api/**").hasAnyRole("ADMIN", "GERENTE")
	        .anyRequest().authenticated()
	    )
	    .httpBasic(withDefaults());
	    */
				

		// CSRF ACTIVADO
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf
				.csrfTokenRequestHandler(csrfRequestHandler)
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			)
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/**").hasAnyRole("ADMIN", "GERENTE")
				.anyRequest().authenticated()
			)
			.httpBasic(withDefaults());


		return http.build();
	}


	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		var config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("*")); // Puedes limitar esto después
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
