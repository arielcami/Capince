package pe.com.capince.service.generic;

import java.util.List;
import java.util.Optional;

public interface ServiceGeneric<T, ID> {
	
    List<T> findAll();
    
    Optional<T> findById(ID id);
    
    T save(T entity);
    
    T update(T entity);
    
    void deleteById(ID id);
    
	boolean existsById(ID id);
}