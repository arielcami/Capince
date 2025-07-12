package pe.com.capince.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFiltroAutenticacion extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	public JwtFiltroAutenticacion(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
		this.jwtUtil = jwtUtil;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String encabezadoAutorizacion = request.getHeader("Authorization");

		String username = null;
		String jwt = null;

		// 1. Extraer el JWT del encabezado Authorization
		if (encabezadoAutorizacion != null && encabezadoAutorizacion.startsWith("Bearer ")) {
			jwt = encabezadoAutorizacion.substring(7); // "Bearer " tiene 7 caracteres
			username = jwtUtil.extraerNombreUsuario(jwt); // Extrae el nombre de usuario del token
		}

		// 2. Si se encuentra un username y no hay autenticación actual en el contexto de seguridad
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Cargar los detalles del usuario
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

			// 3. Validar el token JWT
			if (jwtUtil.validarToken(jwt, userDetails)) {
				// Si el token es válido, crear un objeto de autenticación
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				// Establecer detalles de la autenticación (ej: IP del cliente, sesión ID)
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Establecer la autenticación en el contexto de seguridad de Spring
				// Esto indica a Spring Security que el usuario está autenticado para esta solicitud
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// Continuar con la cadena de filtros de Spring Security
		filterChain.doFilter(request, response);
	}
}