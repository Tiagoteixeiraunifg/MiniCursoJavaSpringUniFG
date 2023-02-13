package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfacesImplementadas.servicos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.minicursoadsfg.minicursoadsfg.api.respostas.Resposta;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AlunoModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.LivroModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.Enumeradores.BibliotecaEnumerador;
import com.minicursoadsfg.minicursoadsfg.dominio.repositorio.AlunoRepositorio;
import com.minicursoadsfg.minicursoadsfg.dominio.repositorio.BibliotecaRepositorio;
import com.minicursoadsfg.minicursoadsfg.dominio.repositorio.LivroRepositorio;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.IBibliotecaServico;


@Component
public class ServicoBibliotecaImpl implements IBibliotecaServico {

	
    BibliotecaRepositorio _bibliotecaRepositorio;
    AlunoRepositorio _alunoRepositorio;
    LivroRepositorio _livroRepositorio;
	
	@Autowired
	public ServicoBibliotecaImpl(BibliotecaRepositorio _bibliotecaRepositorio, AlunoRepositorio _alunoRepositorio, LivroRepositorio _livroRepositorio) {
		this._bibliotecaRepositorio = _bibliotecaRepositorio;
		this._alunoRepositorio = _alunoRepositorio;
		this._livroRepositorio = _livroRepositorio;
	}
	
	
	@Override
	public boolean dataParaEntregaAtrasada(BibliotecaModelo bibliotecaModelo) {
		var data = LocalDateTime.now();
		var resultado = data.isAfter(bibliotecaModelo.getDatareservafim());
		return resultado;
	}

	@Override
	public List<BibliotecaModelo> devolveListaAtrasadosPorQuantidadeDeDias(List<BibliotecaModelo>  biblioteca, Integer diasAconsiderar) {
		var dias = diasAconsiderar <= 0 ? diasAconsiderar = 1 : diasAconsiderar;
		List<BibliotecaModelo> listaCorrigidaEordenada = new ArrayList<>();
		biblioteca.stream().filter(i -> i.estaAtrasadoAentrega(dias)).forEach(x -> listaCorrigidaEordenada.add(x));
		return listaCorrigidaEordenada;
	}

	@Override
	public boolean estaAtrasadoAentrega(BibliotecaModelo biblioteca) {
        
		var data = LocalDateTime.now();
		var resultado = data.isAfter(biblioteca.getDatareservafim());
		return resultado;
		
	}


	@Override
	public Resposta<Void> validaReserva(BibliotecaModelo biblioteca) {
		
		Resposta<Void> resposta = new Resposta<>();
		/*Validando se existe Aluno*/
		if(biblioteca.getAluno().estahPresente()) {
			resposta.adicionarMensagenErroNaListaResposta("Campo aluno não foi inserido");
		}
		
		return resposta;
	}

	@Override
	public BibliotecaModelo iniciarReserva(BibliotecaModelo biblioteca) {
		biblioteca.setDatacadastro(LocalDateTime.now());
		biblioteca.setDataatualizacao(LocalDateTime.now());
		biblioteca.setDatareservainicio(LocalDateTime.now());
		biblioteca.getLivro().stream().forEach(x -> x.reservarLivro());
		return biblioteca;
	}

	@Override
	public BibliotecaModelo finalizarReserva(BibliotecaModelo biblioteca) {
		biblioteca.setStatus(BibliotecaEnumerador.FECHADA);
		biblioteca.setDataatualizacao(LocalDateTime.now());
		biblioteca.setDatadevolucao(LocalDateTime.now());
		biblioteca.getLivro().stream().forEach(x -> x.liberararReservaLivro());
		return biblioteca;
	}


	@Override
	public BibliotecaModelo save(BibliotecaModelo biblioteca) {
		return _bibliotecaRepositorio.save(biblioteca);
	}


	@Override
	public Optional<BibliotecaModelo> findById(Integer biblioteca) {
		return _bibliotecaRepositorio.findById(biblioteca);
		
	}


	@Override
	public void delete(BibliotecaModelo biblioteca) {
	    _bibliotecaRepositorio.delete(biblioteca);
	}
     


	@Override
	public List<BibliotecaModelo> findAll() {
		return  _bibliotecaRepositorio.findAll();
	}


	@Override
	public Optional<AlunoModelo> findAlunoById(Integer id_aluno) {
		return _alunoRepositorio.findById(id_aluno);
	}


	@Override
	public Optional<LivroModelo> findLivroById(Integer id_livro) {
		return _livroRepositorio.findById(id_livro);
	}


	@Override
	public List<BibliotecaModelo> findAllByAluno(AlunoModelo aluno) {
		return _bibliotecaRepositorio.findAllByAluno(aluno);
	}


	@Override
	public List<BibliotecaModelo> devolveListaAtrasadosPorIntervaloDeDatas(List<BibliotecaModelo> biblioteca,
			LocalDateTime dataInicial, LocalDateTime dataFinal) {
		List<BibliotecaModelo> listaCorrigidaEordenada = new ArrayList<>();
		for(BibliotecaModelo reservas : biblioteca) {
				if(reservas.estaAtrasadoAentrega()) {
					if(reservas.getDatareservafim().isAfter(dataInicial) &&
							reservas.getDatareservafim().isBefore(dataFinal)) {
						listaCorrigidaEordenada.add(reservas);
				}
			}
		}
		
		return listaCorrigidaEordenada;
	}



	

}
