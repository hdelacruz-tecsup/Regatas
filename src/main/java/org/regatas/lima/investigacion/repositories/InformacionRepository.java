package org.regatas.lima.investigacion.repositories;

import java.util.List;

import org.regatas.lima.investigacion.entities.Informacion;
import org.springframework.data.repository.CrudRepository;

public interface InformacionRepository extends CrudRepository<Informacion, Long>{
	
	@Override
	List<Informacion> findAll();


}
