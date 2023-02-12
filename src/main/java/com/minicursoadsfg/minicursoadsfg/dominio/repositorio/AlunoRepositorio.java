package com.minicursoadsfg.minicursoadsfg.dominio.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AlunoModelo;

@Repository
public interface AlunoRepositorio extends JpaRepository<AlunoModelo, Integer> {
	
	Optional<AlunoModelo> findById(Integer id);
	
}
