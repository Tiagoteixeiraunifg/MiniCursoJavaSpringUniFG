package com.minicursoadsfg.minicursoadsfg.dominio.modelos;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.AutorDTO;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "Autores")
public class AutorModelo {

	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_autor")
	private Integer id_autor;
	
	@Column(nullable = false, length = 100, name ="nome" )
	private String nome;
	
	@Column(nullable = false, length = 3,name ="idade" )
	private String idade;
	
	@Column(nullable = true, name ="datacadastro" )
	private LocalDateTime datacadastro;
	
	@Column(nullable = true, name = "dataatualizacao")
	private LocalDateTime dataatualizacao;
	
	@ManyToMany(mappedBy = "autor", cascade = CascadeType.MERGE)
	private Set<LivroModelo> livro;
	
	public static AutorModelo novaInstancia() {
		return new AutorModelo();
	}
	
	public AutorDTO convertaModeloParaDto() {
		return new ModelMapper().map(this, AutorDTO.class);
	}
	
	public List<AutorDTO> criarListaAutorDTO(List<AutorModelo> listAutorModelo){
		return  listAutorModelo.stream()
				.map(AutorModelo::convertaModeloParaDto)
				.collect(Collectors.toList());
	}
	
	public AutorModelo preparaCadastroNovo() {
		datacadastro = LocalDateTime.now();
		dataatualizacao = LocalDateTime.now();
		return this;
	}
}
