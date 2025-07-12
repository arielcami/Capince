package pe.com.capince.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.capince.dto.JWTRequest;
import pe.com.capince.dto.JWTResponse;
import pe.com.capince.security.CustomUserDetailsService;
import pe.com.capince.security.JwtUtil;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final CustomUserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;

	public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
			JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping
	public ResponseEntity<?> crearTokenAutenticacion(@RequestBody JWTRequest jwtRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Credenciales incorrectas", e);
		}

		// Si la autenticación fue exitosa, carga los detalles del usuario
		final UserDetails detallesUsuario = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

		// Genera el token JWT
		final String jwt = jwtUtil.generarToken(detallesUsuario);

		// Devuelve el token en la respuesta
		return ResponseEntity.ok(new JWTResponse(jwt)); // Usa JWTResponse
	}
}