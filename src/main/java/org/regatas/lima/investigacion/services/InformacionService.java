package org.regatas.lima.investigacion.services;

import java.util.List;

import org.regatas.lima.investigacion.entities.Informacion;

public interface InformacionService {
	
public List<Informacion> findAll();
	
	public Informacion findById(Long id_if);
	
	public void save(Informacion informacion);
	
	public void deleteById(Long id_if);


}
