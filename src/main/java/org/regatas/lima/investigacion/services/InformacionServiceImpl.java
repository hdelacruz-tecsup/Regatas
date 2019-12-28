package org.regatas.lima.investigacion.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.regatas.lima.investigacion.entities.Informacion;
import org.regatas.lima.investigacion.repositories.InformacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InformacionServiceImpl implements InformacionService {
	
	@Autowired
	private InformacionRepository informacionRepository;


	@Override
	public List<Informacion> findAll() {
		return informacionRepository.findAll();
	}

	@Override
	public Informacion findById(Long id_if) {
		return informacionRepository.findById(id_if).orElseThrow(() -> new EntityNotFoundException("No existe registro"));
	}

	@Override
	public void save(Informacion informacion) {
		informacionRepository.save(informacion);
		
	}

	@Override
	public void deleteById(Long id_if) {
		informacionRepository.deleteById(id_if);
		
	}

}
