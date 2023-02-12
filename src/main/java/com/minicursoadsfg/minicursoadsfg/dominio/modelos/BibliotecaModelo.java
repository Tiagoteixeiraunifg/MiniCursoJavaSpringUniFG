package com.minicursoadsfg.minicursoadsfg.dominio.modelos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.BibliotecaDTO;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.Enumeradores.BibliotecaEnumerador;
import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="biblioteca")
public class BibliotecaModelo {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_biblioteca")
	private Integer id_biblioteca;
	
	
	@OneToOne
	private AlunoModelo aluno;
	

	@ManyToMany
	@JoinTable(name="relacionamento_biblioteca_livro", 
	joinColumns= {@JoinColumn(name="rel_id_livro")},
	inverseJoinColumns= {@JoinColumn(name="rel_id_biblioteca")})
	private Set<LivroModelo> livro;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private BibliotecaEnumerador status;
	
	@Column(nullable = true, name = "dataretirada")
	private LocalDateTime dataretirada;
	
	@Column(nullable = true, name = "datadevolucao")
	private LocalDateTime datadevolucao;
	
	@Column(nullable = true, name = "datareservainicio")
	private LocalDateTime datareservainicio;
	
	@Column(nullable = true, name = "datareservafim")
	private LocalDateTime datareservafim;
	
	@Column(nullable = true, name = "datacadastro")
	private LocalDateTime datacadastro;
	
	@Column(nullable = true, name = "dataatualizacao")
	private LocalDateTime dataatualizacao;
	
	
	
	public static BibliotecaModelo novaInstancia() {
		return new BibliotecaModelo();
	}
	
	public BibliotecaModelo preparaCadastroNovo() {
		status = BibliotecaEnumerador.ABERTA;
		datacadastro = LocalDateTime.now();
		dataatualizacao = LocalDateTime.now();
		return this;
	}
	
	public BibliotecaDTO convertaEntidadeParaDTO() {
		return new ModelMapper().map(this, BibliotecaDTO.class);
	}
	
	public boolean estaAtrasadoAentrega(Integer diasEmAtraso) {
		var resultado = false;
		var data = LocalDateTime.now();
		resultado = data.isAfter(datareservafim);
		if(resultado) {
			var dias = data.getDayOfMonth() - datareservafim.getDayOfMonth();
			resultado =  dias == diasEmAtraso;
		}
		return resultado;
		
	}
	
	public boolean estaAtrasadoAentrega() {
		var resultado = false;
		var data = LocalDateTime.now();
		resultado = data.isAfter(datareservafim);
		return resultado;	
	}
	
	public List<BibliotecaDTO> criarListaBibliotecaDTO(List<BibliotecaModelo> listaBiblioteca){
		return  listaBiblioteca.stream()
				.map(BibliotecaModelo::convertaEntidadeParaDTO)
				.collect(Collectors.toList());
	}

}
