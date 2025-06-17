package pe.com.capince.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pe.com.capince.dto.ClienteDTO;
import pe.com.capince.entity.ClienteEntity;
import pe.com.capince.entity.DistritoEntity;
import pe.com.capince.entity.SexoEntity;
import pe.com.capince.entity.TipoDocumentoEntity;
import pe.com.capince.repository.ClienteRepository;
import pe.com.capince.repository.DistritoRepository;
import pe.com.capince.repository.SexoRepository;
import pe.com.capince.repository.TipoDocumentoRepository;
import pe.com.capince.service.ClienteService;
import pe.com.capince.service.generic.ServiceGenericImpl;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl extends ServiceGenericImpl<ClienteEntity, Long> implements ClienteService {

	private final ClienteRepository clienteRepository;
	private final TipoDocumentoRepository tipoDocumentoRepository;
	private final SexoRepository sexoRepository;
	private final DistritoRepository distritoRepository;
	private final ModelMapper modelMapper;

	public ClienteServiceImpl(ClienteRepository clienteRepository, TipoDocumentoRepository tipoDocumentoRepository,
			SexoRepository sexoRepository, DistritoRepository distritoRepository, ModelMapper modelMapper) {
		super(clienteRepository);
		this.clienteRepository = clienteRepository;
		this.tipoDocumentoRepository = tipoDocumentoRepository;
		this.sexoRepository = sexoRepository;
		this.distritoRepository = distritoRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ClienteDTO> findAllDTO() {
		List<ClienteEntity> entities = clienteRepository.findAll();
		return entities.stream().map(entity -> modelMapper.map(entity, ClienteDTO.class)).toList();
	}

	@Override
	public ClienteDTO findByIdDTO(Long id) {
		return clienteRepository.findById(id).map(entity -> modelMapper.map(entity, ClienteDTO.class)).orElse(null);
	}

	@Override
	public ClienteDTO saveDTO(ClienteDTO dto) {
		// Primero, buscar las entidades relacionadas por nombre en el DTO
		TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByNombre(dto.getTipoDocumento()).orElseThrow(
				() -> new IllegalArgumentException("TipoDocumento no encontrado: " + dto.getTipoDocumento()));

		SexoEntity sexo = sexoRepository.findByNombre(dto.getSexo())
				.orElseThrow(() -> new IllegalArgumentException("Sexo no encontrado: " + dto.getSexo()));

		DistritoEntity distrito = distritoRepository.findByNombre(dto.getDistrito())
				.orElseThrow(() -> new IllegalArgumentException("Distrito no encontrado: " + dto.getDistrito()));

		// Mapear DTO a entidad sin las relaciones aún
		ClienteEntity entity = modelMapper.map(dto, ClienteEntity.class);

		// Setear las relaciones
		entity.setTipoDocumento(tipoDocumento);
		entity.setSexo(sexo);
		entity.setDistrito(distrito);

		// Guardar entidad
		ClienteEntity savedEntity = clienteRepository.save(entity);

		// Mapear entidad guardada a DTO y retornar
		return modelMapper.map(savedEntity, ClienteDTO.class);
	}

	@Override
	public ClienteDTO updateDTO(ClienteDTO dto) {
		if (dto.getId() == null) {
			throw new IllegalArgumentException("El id no puede ser nulo para actualizar");
		}

		Optional<ClienteEntity> existingEntityOpt = clienteRepository.findById(dto.getId());
		if (existingEntityOpt.isEmpty()) {
			throw new IllegalArgumentException("Cliente con id " + dto.getId() + " no existe");
		}

		ClienteEntity entityToUpdate = existingEntityOpt.get();

		// Buscar entidades relacionadas
		TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByNombre(dto.getTipoDocumento()).orElseThrow(
				() -> new IllegalArgumentException("TipoDocumento no encontrado: " + dto.getTipoDocumento()));

		SexoEntity sexo = sexoRepository.findByNombre(dto.getSexo())
				.orElseThrow(() -> new IllegalArgumentException("Sexo no encontrado: " + dto.getSexo()));

		DistritoEntity distrito = distritoRepository.findByNombre(dto.getDistrito())
				.orElseThrow(() -> new IllegalArgumentException("Distrito no encontrado: " + dto.getDistrito()));

		// Actualizar campos desde DTO a la entidad existente
		modelMapper.map(dto, entityToUpdate);

		// Setear las relaciones
		entityToUpdate.setTipoDocumento(tipoDocumento);
		entityToUpdate.setSexo(sexo);
		entityToUpdate.setDistrito(distrito);

		ClienteEntity updatedEntity = clienteRepository.save(entityToUpdate);

		return modelMapper.map(updatedEntity, ClienteDTO.class);
	}

	@Override
	public List<ClienteDTO> findByDocumentoContainingDTO(String documentoFragment) {
		List<ClienteEntity> entities = clienteRepository.findByDocumentoContaining(documentoFragment);
		return entities.stream().map(entity -> modelMapper.map(entity, ClienteDTO.class)).toList();
	}

	 @Override
	    @Transactional
	    public void activar(Long id) {
	        if (!clienteRepository.existsById(id)) {
	            throw new RuntimeException("Cliente no encontrado con ID: " + id);
	        }
	        clienteRepository.activar(id);
	    }

	    @Override
	    @Transactional
	    public void desactivar(Long id) {
	        if (!clienteRepository.existsById(id)) {
	            throw new RuntimeException("Cliente no encontrado con ID: " + id);
	        }
	        clienteRepository.desactivar(id);
	    }

}
