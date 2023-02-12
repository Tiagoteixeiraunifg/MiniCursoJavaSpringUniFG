/**
 * 
 */
package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.LivroModelo;

/**
 * @author tiago
 *
 */
@Component
public interface ILivroServico {

	public boolean livroEhExistente(Integer id_livro);
	
	public List<LivroModelo> findAll();
	
	public LivroModelo save(LivroModelo livroModelo);
	
	public Optional<LivroModelo> findById(Integer id_livro);
	
	public void deleteById(Integer id_livro);
}
