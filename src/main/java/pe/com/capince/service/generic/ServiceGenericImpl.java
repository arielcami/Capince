package pe.com.capince.service.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public class ServiceGenericImpl<T, ID> implements ServiceGeneric<T, ID> {

    protected final JpaRepository<T, ID> repository;

    public ServiceGenericImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
    
    

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public T update(T entity) {
        // Para actualizar, primero verificamos que exista la entidad
        // Asumimos que la entidad tiene un método getId() o que se pasa la id de alguna forma,
        // pero como esta clase es genérica no conocemos el método getId().
        // Por eso, normalmente el update se hace a través de find + merge o se recibe la entidad ya con ID existente.

        // Aquí, una implementación simple podría ser:
        if(entity == null) {
            throw new IllegalArgumentException("Entity to update cannot be null");
        }
        // Si el repositorio usa save para crear y actualizar (como JpaRepository), solo llamamos a save.
        // Porque JpaRepository.save() hace insert o update dependiendo si el id está presente.

        return repository.save(entity);
    }

}