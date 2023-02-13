package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AlunoModelo;

@Component
public interface IAlunoServico {
	
	public List<AlunoModelo> findAll();
	
	public AlunoModelo save(AlunoModelo aluno);
	
	public Optional<AlunoModelo> findById(Integer id_aluno);
	
	public void deleteById(Integer id_aluno);
	
	public Optional<AlunoModelo> findByEmail(String email);

}
