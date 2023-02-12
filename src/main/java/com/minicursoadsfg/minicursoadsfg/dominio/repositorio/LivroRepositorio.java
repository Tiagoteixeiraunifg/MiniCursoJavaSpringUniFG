package com.minicursoadsfg.minicursoadsfg.dominio.repositorio;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minicursoadsfg.minicursoadsfg.dominio.modelos.LivroModelo;

@Repository
public interface LivroRepositorio extends JpaRepository<LivroModelo, Integer> {

	Optional<LivroModelo> findById(Integer id_livro);
	
}
