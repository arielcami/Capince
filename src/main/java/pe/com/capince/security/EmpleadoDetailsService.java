package pe.com.capince.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.com.capince.entity.EmpleadoEntity;
import pe.com.capince.repository.EmpleadoRepository;

@Service
public class EmpleadoDetailsService implements UserDetailsService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoDetailsService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
        EmpleadoEntity empleado = empleadoRepository.findByDocumento(documento)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado con documento: " + documento));

        if (!empleado.isEstado()) {
            throw new UsernameNotFoundException("Empleado inactivo");
        }

        String role = empleado.getRol().getNombre().toUpperCase();

        return User.builder()
                .username(empleado.getDocumento())
                .password(empleado.getClave()) // clave ya encriptada
                .roles(role)
                .build();
    }
}
