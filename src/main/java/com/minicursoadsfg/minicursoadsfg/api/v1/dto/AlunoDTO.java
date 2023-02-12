package com.minicursoadsfg.minicursoadsfg.api.v1.dto;


import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AlunoModelo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Valid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
	
	
	@NotNull(groups = ValidacoesEmGrupo.id_aluno.class)
	private Integer id_aluno;
	
	@NotBlank(message = "O campo nome não pode está em branco")
	@NotNull(message = "O campo nome não pode ser nulo")
	private String  nome;
	
	@NotBlank(message = "O campo sobrenome não pode está em branco")
	@NotNull(message = "O campo sobrenome não pode ser nulo")
	private String	sobrenome;
	
	@NotBlank(message = "O campo idade não pode está em branco")
	@NotNull(message = "O campo idade não pode ser nulo")
	private String	idade;
	
	@NotBlank(message = "O campo telefone não pode está em branco")
	@NotNull(message = "O campo telefone não pode ser nulo")
	private String	telefone;
	
	@Email
	@NotBlank(message = "O campo email telefone não pode está em branco")
	@NotNull(message = "O campo email telefone não pode ser nulo")
	private String	email;
		
	public AlunoModelo convertaDtoParaModelo() {
		return new ModelMapper().map(this, AlunoModelo.class);
	}
	
}
