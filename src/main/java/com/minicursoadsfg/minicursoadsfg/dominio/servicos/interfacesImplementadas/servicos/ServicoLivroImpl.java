/**
 * 
 */
package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfacesImplementadas.servicos;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

import com.minicursoadsfg.minicursoadsfg.dominio.modelos.LivroModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.repositorio.LivroRepositorio;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.ILivroServico;

/**
 * @author tiago
 *
 */
@Component
public class ServicoLivroImpl implements ILivroServico {

	
	private LivroRepositorio _repositorioLivro;
	
	public ServicoLivroImpl(LivroRepositorio _repositorioLivro) {
		this._repositorioLivro = _repositorioLivro;
	} 
	
	@Override
	@Transactional
	public boolean livroEhExistente(Integer id_livro) {
		var resultado = _repositorioLivro.existsById(id_livro);
		return resultado;
	}

	@Override
	public List<LivroModelo> findAll() {
		return _repositorioLivro.findAll();
	}

	@Override
	public LivroModelo save(LivroModelo livroModelo) {
		return _repositorioLivro.save(livroModelo);
	}

	@Override
	public Optional<LivroModelo> findById(Integer id_livro) {
		return _repositorioLivro.findById(id_livro);
	}

	@Override
	public void deleteById(Integer id_livro) {
		_repositorioLivro.deleteById(id_livro);
	}

}
