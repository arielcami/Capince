package pe.com.capince.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.capince.entity.ClienteEntity;

@Configuration
public class ConfigModelMapper {

    public static class EntityToStringConverter<T> extends AbstractConverter<T, String> {
        @Override
        protected String convert(T source) {
            if (source == null) return null;
            try {
                return (String) source.getClass().getMethod("getNombre").invoke(source);
            } catch (Exception e) {
                return null;
            }
        }
    }

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Converter genérico para convertir entidades a su "nombre"
        Converter<Object, String> entityToStringConverter = new EntityToStringConverter<>();
        modelMapper.addConverter(entityToStringConverter);

        // Mapear explícitamente campos complejos para ClienteEntity -> ClienteDTO
        modelMapper.typeMap(ClienteEntity.class, pe.com.capince.dto.ClienteDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getTipoDocumento().getNombre(), pe.com.capince.dto.ClienteDTO::setTipoDocumento);
            mapper.map(src -> src.getSexo().getNombre(), pe.com.capince.dto.ClienteDTO::setSexo);
            mapper.map(src -> src.getDistrito().getNombre(), pe.com.capince.dto.ClienteDTO::setDistrito);
        });

        return modelMapper;
    }
}
