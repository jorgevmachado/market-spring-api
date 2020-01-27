package br.com.jorgevmachado.marketSpringApi.services;

import java.util.List;

import br.com.jorgevmachado.marketSpringApi.domain.Estado;
import br.com.jorgevmachado.marketSpringApi.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;
	
	public List<Estado> findAll() {
		return repository.findAllByOrderByNome();
	}
}
