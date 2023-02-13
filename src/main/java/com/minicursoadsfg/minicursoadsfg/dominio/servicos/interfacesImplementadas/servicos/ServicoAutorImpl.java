/**
 * 
 */
package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfacesImplementadas.servicos;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AutorModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.repositorio.AutorRepositorio;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.IAutorServico;

/**
 * @author tiago
 *
 */
@Component
public class ServicoAutorImpl implements IAutorServico {

	AutorRepositorio _AutorRepositorio;
	
	public ServicoAutorImpl(AutorRepositorio _AutorRepositorio) {
		this._AutorRepositorio = _AutorRepositorio;
	}
	
	@Override
	public List<AutorModelo> findAll() {
		return _AutorRepositorio.findAll();
	}

	@Override
	public AutorModelo save(AutorModelo autorModelo) {
		return _AutorRepositorio.save(autorModelo);
	}

	@Override
	public Optional<AutorModelo> findById(Integer id_autor) {
		return _AutorRepositorio.findById(id_autor);
	}

	@Override
	public void deleteById(Integer id_autor) {
		_AutorRepositorio.deleteById(id_autor);
	}


}
