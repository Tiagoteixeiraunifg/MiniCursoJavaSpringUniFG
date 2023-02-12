/**
 * 
 */
package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfacesImplementadas.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AlunoModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.repositorio.AlunoRepositorio;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.IAlunoServico;

/**
 * @author tiago
 *
 */
@Component
public class ServicoAlunoImpl implements IAlunoServico {
	
	private AlunoRepositorio _alunoRepositorio;
	
	public ServicoAlunoImpl(AlunoRepositorio _alunoRepositorio) {
		this._alunoRepositorio = _alunoRepositorio;
	}

	@Override
	public List<AlunoModelo> findAll() {
		return _alunoRepositorio.findAll();
	}

	@Override
	public AlunoModelo save(AlunoModelo aluno) {
		
		return _alunoRepositorio.save(aluno);
	}

	@Override
	public Optional<AlunoModelo> findById(Integer id_aluno) {
		return _alunoRepositorio.findById(id_aluno);
	}

	@Override
	public void deleteById(Integer id_aluno) {
		_alunoRepositorio.deleteById(id_aluno);
	}

}
