package com.minicursoadsfg.minicursoadsfg.api.v1.dto;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AutorModelo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Valid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {
	
	
	Integer id_autor;
	
	@NotBlank(message = "O campo nome não pode está em branco")
	@NotNull(message = "O campo nome não pode ser nulo")
	String nome;
	
	@NotBlank(message = "O campo idade não pode está em branco")
	@NotNull(message = "O campo idade não pode ser nulo")
	String idade;
	

	public AutorModelo convertaDtoParaModelo() {
		return new ModelMapper().map(this, AutorModelo.class);
	}
}
