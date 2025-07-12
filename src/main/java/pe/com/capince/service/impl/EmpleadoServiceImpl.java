package pe.com.capince.service.impl;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import pe.com.capince.dto.EmpleadoDTO;
import pe.com.capince.entity.DistritoEntity;
import pe.com.capince.entity.EmpleadoEntity;
import pe.com.capince.entity.RolEntity;
import pe.com.capince.entity.SexoEntity;
import pe.com.capince.entity.TipoDocumentoEntity;
import pe.com.capince.repository.DistritoRepository;
import pe.com.capince.repository.EmpleadoRepository;
import pe.com.capince.repository.RolRepository;
import pe.com.capince.repository.SexoRepository;
import pe.com.capince.repository.TipoDocumentoRepository;
import pe.com.capince.service.EmpleadoService;
import pe.com.capince.service.generic.ServiceGenericImpl;

@Service
public class EmpleadoServiceImpl extends ServiceGenericImpl<EmpleadoEntity, Long> implements EmpleadoService {

	private final EmpleadoRepository empleadoRepository;
	private final DistritoRepository distritoRepository;
	private final RolRepository rolRepository;
	private final TipoDocumentoRepository tipoDocumentoRepository;
	private final SexoRepository sexoRepository;
	private final PasswordEncoder passwordEncoder;

	public EmpleadoServiceImpl(
			EmpleadoRepository empleadoRepository, 
			DistritoRepository distritoRepository,
			RolRepository rolRepository, 
			TipoDocumentoRepository tipoDocumentoRepository, 
			SexoRepository sexoRepository,
			PasswordEncoder passwordEncoder) {
		super(empleadoRepository);
		this.empleadoRepository = empleadoRepository;
		this.distritoRepository = distritoRepository;
		this.rolRepository = rolRepository;
		this.tipoDocumentoRepository = tipoDocumentoRepository;
		this.sexoRepository = sexoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public EmpleadoDTO saveDTO(EmpleadoDTO dto) {
		EmpleadoEntity entity = new EmpleadoEntity();

		entity.setNombres(dto.getNombres());
		entity.setApellidoPaterno(dto.getApellidoPaterno());
		entity.setApellidoMaterno(dto.getApellidoMaterno());
		entity.setTelefono(dto.getTelefono());
		entity.setUsername(dto.getUsername());
		entity.setDocumento(dto.getDocumento());

		// Conversión de String a entidad
		TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByNombre(dto.getTipoDocumento())
				.orElseThrow(() -> new RuntimeException("Tipo de documento no válido: " + dto.getTipoDocumento()));
		SexoEntity sexo = sexoRepository.findByNombre(dto.getSexo())
				.orElseThrow(() -> new RuntimeException("Sexo no válido: " + dto.getSexo()));
		DistritoEntity distrito = distritoRepository.findByNombre(dto.getDistrito())
				.orElseThrow(() -> new RuntimeException("Distrito no encontrado: " + dto.getDistrito()));
		RolEntity rol = rolRepository.findByNombre(dto.getRol())
				.orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRol()));

		entity.setTipoDocumento(tipoDocumento);
		entity.setSexo(sexo);
		entity.setDireccion(dto.getDireccion());
		entity.setEstado(dto.isEstado());
		entity.setDistrito(distrito);
		entity.setRol(rol);

		// Validar que la clave venga y cifrarla antes de guardar
		if (dto.getPassword() == null || dto.getPassword().isBlank()) {
			throw new IllegalArgumentException("La clave es obligatoria al crear un empleado.");
		}
		
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));

		EmpleadoEntity guardado = empleadoRepository.save(entity);

		dto.setId(guardado.getId().longValue());
		dto.setPassword(null);

		return dto;
	}

	@Override
	public EmpleadoDTO findByIdDTO(Long id) {
		return empleadoRepository.findById(id).map(this::toDTO)
				.orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
	}

	@Override
	public List<EmpleadoDTO> findAllDTO() {
		return empleadoRepository.findAll().stream().map(this::toDTO).toList();
	}

	// Conversión de Entity a DTO
	private EmpleadoDTO toDTO(EmpleadoEntity entity) {
		EmpleadoDTO dto = new EmpleadoDTO();

		dto.setId(entity.getId().longValue());
		dto.setNombres(entity.getNombres());
		dto.setApellidoPaterno(entity.getApellidoPaterno());
		dto.setApellidoMaterno(entity.getApellidoMaterno());
		dto.setTelefono(entity.getTelefono());
		dto.setUsername(entity.getUsername());
		dto.setDocumento(entity.getDocumento());
		dto.setTipoDocumento(entity.getTipoDocumento().getNombre());
		dto.setSexo(entity.getSexo().getNombre());
		dto.setDireccion(entity.getDireccion());
		dto.setEstado(entity.isEstado());
		dto.setDistrito(entity.getDistrito().getNombre());
		dto.setRol(entity.getRol().getNombre());
		dto.setPassword(null);

		return dto;
	}

	@Override
	public EmpleadoDTO updateDTO(Long id, EmpleadoDTO dto) {
		EmpleadoEntity entity = empleadoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

		entity.setNombres(dto.getNombres());
		entity.setApellidoPaterno(dto.getApellidoPaterno());
		entity.setApellidoMaterno(dto.getApellidoMaterno());
		entity.setTelefono(dto.getTelefono());
		entity.setUsername(dto.getUsername());
		entity.setDocumento(dto.getDocumento());

		TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByNombre(dto.getTipoDocumento())
				.orElseThrow(() -> new RuntimeException("Tipo de documento no válido: " + dto.getTipoDocumento()));
		SexoEntity sexo = sexoRepository.findByNombre(dto.getSexo())
				.orElseThrow(() -> new RuntimeException("Sexo no válido: " + dto.getSexo()));
		DistritoEntity distrito = distritoRepository.findByNombre(dto.getDistrito())
				.orElseThrow(() -> new RuntimeException("Distrito no encontrado: " + dto.getDistrito()));
		RolEntity rol = rolRepository.findByNombre(dto.getRol())
				.orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRol()));

		entity.setTipoDocumento(tipoDocumento);
		entity.setSexo(sexo);
		entity.setDireccion(dto.getDireccion());
		entity.setEstado(dto.isEstado());
		entity.setDistrito(distrito);
		entity.setRol(rol);

		// Solo cifrar y actualizar la contraseña si se proporciona una nueva
		if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		}

		EmpleadoEntity actualizado = empleadoRepository.save(entity);
		
		EmpleadoDTO retornoDTO = toDTO(actualizado);
		retornoDTO.setPassword(null); // Reasegurar que la clave no se envíe
		return retornoDTO;
	}

	@Override
	public void deleteByIdDTO(Long id) {
		if (!empleadoRepository.existsById(id)) {
			throw new RuntimeException("Empleado no encontrado con ID: " + id);
		}
		empleadoRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void activar(Long id) {
		if (!empleadoRepository.existsById(id)) {
			throw new EntityNotFoundException("Empleado no encontrado con id: " + id);
		}
		empleadoRepository.activar(id);
	}

	@Override
	@Transactional
	public void desactivar(Long id) {
		if (!empleadoRepository.existsById(id)) {
			throw new EntityNotFoundException("Empleado no encontrado con id: " + id);
		}
		empleadoRepository.desactivar(id);
	}

}