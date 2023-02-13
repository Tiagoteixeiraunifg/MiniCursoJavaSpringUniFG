package com.minicursoadsfg.minicursoadsfg.api.v1.dto;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.LivroModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.Enumeradores.LivroStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Valid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {
	
	@NotNull(groups = ValidacoesEmGrupo.id_livro.class)
	private Integer id_livro;
	
	@NotBlank(message = "O campo nome não pode está em branco")
	@NotNull(message = "O campo nome não pode ser nulo")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "O campo status não pode ser nulo")
	private LivroStatusEnum status;
		
	@NotNull(message = "O autor tem de ser informado")
	private Set<AutorDTO> autor;
	
	

	
	
	public LivroModelo convertaDtoParaModelo() {
		return new ModelMapper().map(this, LivroModelo.class);
	}
}
