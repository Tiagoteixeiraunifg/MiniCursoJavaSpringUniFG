package com.minicursoadsfg.minicursoadsfg.api.v1.dto;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.modelmapper.ModelMapper;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Valid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BibliotecaDTO {
	
	
	private Integer id_biblioteca;
	
	
	@Valid
	@ConvertGroup(from = Default.class , to = ValidacoesEmGrupo.id_aluno.class)
	private AlunoDTO aluno;
	
	//@ConvertGroup(from = Default.class , to = ValidacoesEmGrupo.id_livro.class)
	@NotNull(message = "O campo livro não pode ser nulo")
	private Set<LivroDTO> livro;
	
	
	@NotNull
	@Pattern(regexp="^(ABERTA|FECHADA)$", 
	message="O tipo STATUS esperado é ABERTO ou FECHADO")
	private String status;
	
	
	private LocalDateTime dataretirada;
		

	@NotNull(message = "O campo datadevolucao não pode ser nulo")
	private LocalDateTime datadevolucao;
	

	@NotNull(message = "O campo datareservainicio não pode ser nulo")
	private LocalDateTime datareservainicio;
	
	
	private LocalDateTime datareservafim;


	public BibliotecaModelo convertaDtoParaEntidade() {
		return new ModelMapper().map(this, BibliotecaModelo.class);
	}
	
	
}
