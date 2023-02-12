package com.minicursoadsfg.minicursoadsfg.dominio.modelos;
import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.AlunoDTO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Alunos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AlunoModelo {
	
	
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "id_aluno")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_aluno;

	@Column(nullable = false, length = 100)
	private String  nome;
	
	@Column(nullable = false, length = 100)
	private String	sobrenome;
	
	@Column(nullable = false, length = 3)
	private String	idade;
	
	@Column(nullable = false, length = 20)
	private String	telefone;
	
	@Column(nullable = false, length = 100)
	private String	email;
	
	@Column(nullable = true)
	private LocalDateTime datacadastro;
	
	@Column(nullable = true)
	private LocalDateTime dataatualizacao;
	
	public static AlunoModelo novaInstancia() {
		return new AlunoModelo();
	}
	
	public AlunoModelo preparaCadastroNovo() {
		datacadastro = LocalDateTime.now();
		dataatualizacao = LocalDateTime.now();
		return this;
	}
	
	public boolean estahPresente() {
		var resultado = false;
		resultado = nome.length() > 1 || nome.isBlank()  ? true : false;
		resultado = id_aluno >= 1 ? true : false;
		return resultado;
	}
	
	public AlunoDTO convertaModeloParaDto() {
		return new ModelMapper().map(this, AlunoDTO.class);
	}
	
	public List<AlunoDTO> criarListaAlunoDTO(List<AlunoModelo> listAutorModelo){
		return  listAutorModelo.stream()
				.map(AlunoModelo::convertaModeloParaDto)
				.collect(Collectors.toList());
	}
		
}
