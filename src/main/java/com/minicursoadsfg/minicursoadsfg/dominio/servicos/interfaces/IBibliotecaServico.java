package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AlunoModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.LivroModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.validacoes.IBibliotecaValidacoes;


@Component
public interface IBibliotecaServico extends IBibliotecaValidacoes {
	
	public boolean dataParaEntregaAtrasada(BibliotecaModelo biblioteca);
	
	public List<BibliotecaModelo> devolveListaAtrasadosPorQuantidadeDeDias(List<BibliotecaModelo> biblioteca, Integer diasAconsiderar);
	
	public List<BibliotecaModelo> devolveListaAtrasadosPorIntervaloDeDatas(List<BibliotecaModelo> biblioteca, LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	abstract  boolean estaAtrasadoAentrega(BibliotecaModelo biblioteca);

	public BibliotecaModelo iniciarReserva(BibliotecaModelo biblioteca);
	
	public BibliotecaModelo finalizarReserva(BibliotecaModelo biblioteca);

	public BibliotecaModelo save(BibliotecaModelo biblioteca);
	
	public Optional<BibliotecaModelo> findById(Integer biblioteca);
		
	public void delete(BibliotecaModelo biblioteca);
	
	public List<BibliotecaModelo> findAll();
	
	public List<BibliotecaModelo> findAllByAluno(AlunoModelo aluno);
	
	public Optional<AlunoModelo> findAlunoById(Integer id_aluno);
	
	public Optional<LivroModelo> findLivroById(Integer id_livro);
	
}
