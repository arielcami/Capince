package pe.com.capince.security;

import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.com.capince.entity.EmpleadoEntity;
import pe.com.capince.repository.EmpleadoRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final EmpleadoRepository empleadoRepository;

	public CustomUserDetailsService(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		EmpleadoEntity empleado = empleadoRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		if (!empleado.isEstado()) {
			throw new UsernameNotFoundException("Usuario inactivo: " + username);
		}

		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + empleado.getRol().getNombre());

		return new org.springframework.security.core.userdetails.User(empleado.getUsername(), empleado.getPassword(),
				Collections.singletonList(authority)
		);
	}
}