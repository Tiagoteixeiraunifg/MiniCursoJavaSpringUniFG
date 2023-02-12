package com.minicursoadsfg.minicursoadsfg.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AlunoModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;


@Repository
public interface BibliotecaRepositorio extends JpaRepository<BibliotecaModelo, Integer> {

	public List<BibliotecaModelo> findByAlunoNome(String parametro);

	public List<BibliotecaModelo> findAllByAluno(AlunoModelo aluno);
	
	public Optional<BibliotecaModelo> findById(Integer id);
	
	//HQL para join entre a entidade Biblioteca e Aluno	
	@Query(value="FROM BibliotecaModelo biblioteca JOIN FETCH biblioteca.aluno")
	public List<BibliotecaModelo> findAll();
	
}
