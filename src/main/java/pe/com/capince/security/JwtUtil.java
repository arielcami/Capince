package pe.com.capince.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${jwt.secret}")
	private String jwtSecreto;

	// Duración del token JWT en milisegundos (5 horas)
	private int jwtExpiracionMs = 18000000;

	/**
	 * Obtiene la clave de firma secreta para JWT. Convierte la cadena secreta
	 * directamente a bytes.
	 * 
	 * @return Objeto Key para la firma.
	 */
	private Key obtenerClaveDeFirma() {
		// Convierte la cadena secreta directamente a bytes usando UTF-8.
		// jwt.secret en application.properties no es una cadena Base64.
		byte[] bytesClave = jwtSecreto.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(bytesClave);
	}

	/**
	 * Extrae un 'claim' específico del token JWT.
	 * 
	 * @param token          El token JWT.
	 * @param claimsResolver Función para resolver el claim deseado.
	 * @param <T>            Tipo del claim.
	 * @return El valor del claim.
	 */
	public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extraerTodosLosClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extrae todos los 'claims' (cuerpo) del token JWT.
	 * 
	 * @param token El token JWT.
	 * @return Objeto Claims que contiene todos los datos del token.
	 */
	private Claims extraerTodosLosClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(obtenerClaveDeFirma()).build().parseClaimsJws(token).getBody();
	}

	/**
	 * Extrae el nombre de usuario (subject) del token JWT.
	 * 
	 * @param token El token JWT.
	 * @return El nombre de usuario.
	 */
	public String extraerNombreUsuario(String token) {
		return extraerClaim(token, Claims::getSubject);
	}

	/**
	 * Valida si token JWT es válido para un usuario dado. Comprueba que el
	 * nombre de usuario coincida y que el token no esté expirado.
	 * 
	 * @param token           El token JWT.
	 * @param detallesUsuario Los detalles del usuario cargados por
	 *                        UserDetailsService.
	 * @return true si el token es válido, false en caso contrario.
	 */
	public boolean validarToken(String token, UserDetails detallesUsuario) {
		final String nombreUsuario = extraerNombreUsuario(token);
		return (nombreUsuario.equals(detallesUsuario.getUsername()) && !esTokenExpirado(token));
	}

	/**
	 * Verifica si el token JWT ha expirado.
	 * 
	 * @param token El token JWT.
	 * @return true si el token ha expirado, false en caso contrario.
	 */
	private boolean esTokenExpirado(String token) {
		return extraerFechaExpiracion(token).before(new Date());
	}

	/**
	 * Extrae la fecha de expiración del token JWT.
	 * 
	 * @param token El token JWT.
	 * @return La fecha de expiración.
	 */
	private Date extraerFechaExpiracion(String token) {
		return extraerClaim(token, Claims::getExpiration);
	}

	/**
	 * Genera un token JWT para un usuario.
	 * 
	 * @param detallesUsuario Los detalles del usuario para los que se genera el
	 *                        token.
	 * @return El token JWT generado como String.
	 */
	public String generarToken(UserDetails detallesUsuario) {
		Map<String, Object> claims = new HashMap<>();
		// Aquí podrías añadir claims adicionales como roles, ID de usuario, etc.
		// Por ejemplo:
		// claims.put("roles", detallesUsuario.getAuthorities().stream()
		// .map(GrantedAuthority::getAuthority)
		// .collect(Collectors.toList()));
		return crearToken(claims, detallesUsuario.getUsername());
	}

	/**
	 * Crea el token JWT con los claims, el sujeto (nombre de usuario), fecha de
	 * emisión y expiración.
	 * 
	 * @param claims Mapa de claims adicionales.
	 * @param sujeto El sujeto del token (generalmente el nombre de usuario).
	 * @return El token JWT compactado como String.
	 */
	private String crearToken(Map<String, Object> claims, String sujeto) {
		return Jwts.builder().setClaims(claims).setSubject(sujeto) // Nombre de usuario
				.setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiracionMs)) // Fecha de expiración
				.signWith(obtenerClaveDeFirma(), SignatureAlgorithm.HS256) // Firma el token
				.compact(); // Compacta a String
	}

	/**
	 * Valida la integridad y validez general de un token JWT. Captura y registra
	 * excepciones comunes de JWT.
	 * 
	 * @param tokenAutenticacion El token JWT a validar.
	 * @return true si el token es válido y su firma es correcta, false en caso
	 *         contrario.
	 */
	public boolean validarJwtToken(String tokenAutenticacion) {
		try {
			Jwts.parserBuilder().setSigningKey(obtenerClaveDeFirma()).build().parseClaimsJws(tokenAutenticacion);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Token JWT inválido: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Token JWT expirado: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Token JWT no soportado: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("La cadena de claims JWT está vacía: {}", e.getMessage());
		}
		return false;
	}
}
