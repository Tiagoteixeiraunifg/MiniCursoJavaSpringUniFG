package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AutorModelo;

@Component
public interface IAutorServico {
	
	public List<AutorModelo> findAll();
	
	public AutorModelo save(AutorModelo autorModelo);
	
	public Optional<AutorModelo> findById(Integer id_autor);
	
	public void deleteById(Integer id_autor);
	
	
}

