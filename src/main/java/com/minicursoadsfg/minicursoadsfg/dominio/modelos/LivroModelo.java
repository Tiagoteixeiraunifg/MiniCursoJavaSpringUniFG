package com.minicursoadsfg.minicursoadsfg.dominio.modelos;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.LivroDTO;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.Enumeradores.LivroStatusEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="Livros")
public class LivroModelo {
	
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Livro")
	private Integer id_livro;
	
	@Column(name = "nome")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@JsonProperty(access = Access.READ_ONLY)
	private LivroStatusEnum status;
	

	@ManyToMany
	@JoinTable(name="relacionamento_livro_autor", 
	joinColumns= @JoinColumn(name="rel_id_livro", referencedColumnName = "id_livro"),
	inverseJoinColumns= @JoinColumn(name="rel_id_autor", referencedColumnName = "id_autor"))
	private Set<AutorModelo> autor;
	

	@Column(nullable = true, name = "datacadastro")
	private LocalDateTime datacadastro;
	
	@Column(nullable = true, name = "dataatualizacao")
	private LocalDateTime dataatualizacao;
	
	/*METODOS*/
	public static LivroModelo novaInstancia() {
		return new LivroModelo();
	}
	
	public LivroStatusEnum statusDoLivro() {
		return status;
	}
	
	public Boolean estahLiberado() {
		return status == LivroStatusEnum.LIVRE;
	}
	
	public void reservarLivro() {
		status = LivroStatusEnum.RESERVADO;
	}
	
	public void liberararReservaLivro() {
		status = LivroStatusEnum.LIVRE;
	}
	
	public LivroDTO convertaEntidadeParaDto() {
		return new ModelMapper().map(this, LivroDTO.class);
	}
	
	public List<LivroDTO> criarListaLivroDTO(List<LivroModelo> listaLivroModelo){
		return  listaLivroModelo.stream()
				.map(LivroModelo::convertaEntidadeParaDto)
				.collect(Collectors.toList());
	}
	
	public LivroModelo preparaCadastroNovo() {
		datacadastro = LocalDateTime.now();
		dataatualizacao = LocalDateTime.now();
		return this;
	}
}
