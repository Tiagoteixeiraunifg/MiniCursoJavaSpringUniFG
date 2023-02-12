/**
 * 
 */
package com.minicursoadsfg.minicursoadsfg.api.v1.controles;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.minicursoadsfg.minicursoadsfg.api.respostas.Resposta;
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.BibliotecaDTO;
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.LivroDTO;
import com.minicursoadsfg.minicursoadsfg.comum.UtilApi;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.IAlunoServico;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.IBibliotecaServico;
import com.minicursoadsfg.minicursoadsfg.dominio.FabricaExcecoes.NaoEncontradoExcepition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



/**
 * @author tiago teixeira
 *
 */


@RestController
@CrossOrigin(origins = "${front.baseurl}")
@RequestMapping(value = "/api/v1/biblioteca", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Operações relacionados as funcionalidades da biblioteca EXEMPLO")
public class BibliotecaControle {
	

	private IBibliotecaServico servico;
	private IAlunoServico _servicoAluno;
	private UtilApi _utilApi;
	
	@Autowired
	public BibliotecaControle(IBibliotecaServico servico, IAlunoServico _servicoAluno, UtilApi _utilApi) {
		this.servico = servico;
		this._servicoAluno = _servicoAluno;
		this._utilApi = _utilApi;
	}
	
	
	
	/**
	 * Obtem todas as reservas
	 * @return
	 */
	@GetMapping
	@ApiOperation(value = "Obtém todos os registros de livros em poder dos alunos")
	public ResponseEntity<Resposta<List<BibliotecaDTO>>> obterTodos(){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<List<BibliotecaDTO>> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
			
		resposta.setObjeto(BibliotecaModelo.novaInstancia().criarListaBibliotecaDTO(servico.findAll()));
		

		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
		
	} 
	
	
	/**
	 * Obtem todas reservas atrasadas de entrega conforme a quantidade de dias em atraso.
	 * @param diasAconsiderar
	 * @return
	 */
	@GetMapping(value = "/{diasAconsiderar}")
	@ApiOperation(value = "Obtém todos os registros de livros em poder dos alunos com atraso determinado por valor")
	public ResponseEntity<Resposta<List<BibliotecaDTO>>> obterTodosAtrasados(@PathVariable Integer diasAconsiderar){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<List<BibliotecaDTO>> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		
		if(diasAconsiderar <= 0 || diasAconsiderar == null) {
			resposta.adicionarMensagenErroNaResposta("O valor de dias a considerar tem de ser inserido!");
			return new ResponseEntity<>(resposta, headers, HttpStatus.BAD_REQUEST);
		}
		
		var ListaComAtrasados = servico.devolveListaAtrasadosPorQuantidadeDeDias(servico.findAll(), diasAconsiderar);
		resposta.setObjeto(BibliotecaModelo.novaInstancia().criarListaBibliotecaDTO(ListaComAtrasados));
		
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
		
	} 
	
	/**
	 * Obtem as reserva de um aluno especifico.
	 * @param diasAconsiderar
	 * @return
	 */
	@GetMapping(value = "/especifico/{idAluno}")
	@ApiOperation(value = "Obtém todos os registros de livros em poder do aluno informado")
	public ResponseEntity<Resposta<List<BibliotecaDTO>>> obterTodosDoAluno(@PathVariable Integer idAluno) throws NaoEncontradoExcepition{
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<List<BibliotecaDTO>> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if(idAluno <= 0 || idAluno == null) {
			resposta.adicionarMensagenErroNaResposta("O valor de dias a considerar tem de ser inserido!");
			return new ResponseEntity<>(resposta, headers, HttpStatus.BAD_REQUEST);
		}
		
		var aluno = _servicoAluno.findById(idAluno);
		if(!aluno.isPresent()) {
			throw new NaoEncontradoExcepition("Nao encontrado o Aluno com o ID informado");
		}
		
		var lista = servico.findAllByAluno(aluno.get());
		
		if(lista.size() <= 0 || lista.isEmpty()) {
			throw new NaoEncontradoExcepition("Nao encontrado reservas para o Aluno com o ID informado");
		}
		
		
		resposta.setObjeto(BibliotecaModelo.novaInstancia().criarListaBibliotecaDTO(lista));

		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
		
	} 
	
	
	/**
	 * Cadastrar nova reserva de Livro para Aluno.
	 * @param BibliotecaDTO
	 * @param resultado
	 * @return
	 * @throws NaoEncontradoExcepition
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Cadastra uma nova reserva.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resposta<BibliotecaDTO>> cadastreReserva(@Valid @RequestBody BibliotecaDTO BibliotecaDTO, BindingResult resultado)
			throws NaoEncontradoExcepition {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<BibliotecaDTO> resposta = new Resposta<>();
		
		if (resultado.hasErrors()) {
			resultado.getAllErrors().stream().forEach(error -> resposta.adicionarMensagenErroNaListaResposta(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}
		
		
		if(!servico.findAlunoById(BibliotecaDTO.getAluno().getId_aluno()).isPresent()) {
			throw new  NaoEncontradoExcepition("Não encontrammos esse aluno no banco de dados");
		}
		
		
		for (LivroDTO livros : BibliotecaDTO.getLivro() ) {
			if(!servico.findLivroById(livros.getId_livro()).isPresent()) {
				throw new  NaoEncontradoExcepition("Não encontrammos o livro de codigo: "+livros.getId_livro().toString()+" no banco de dados");
			}
			
		}
		
		var convertidoEmModelo = BibliotecaDTO.convertaDtoParaEntidade();
		var resultadoRepositorio =  servico.save(convertidoEmModelo.preparaCadastroNovo());
		resposta.setObjeto(resultadoRepositorio.convertaEntidadeParaDTO());
		
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	
	/**
	 * Responsável por Finalizar a Reserva do Livro.
	 * @param dataEntrega
	 * @param idDaReserva
	 * @return
	 * @throws NaoEncontradoExcepition
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Finalizar uma reserva.")
	@PostMapping(value = "/{dataEntrega}&idReserva={idDaReserva}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resposta<BibliotecaDTO>> finalizarReserva(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataEntrega, 
			@RequestParam Integer idDaReserva) throws NaoEncontradoExcepition {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<BibliotecaDTO> resposta = new Resposta<>();
						
		var dataRecebida = UtilApi.converterLocalDateParaLocalDateTime(dataEntrega);
		
		var biblioteca = servico.findById(idDaReserva);
		
		if(!biblioteca.isPresent()) {
			throw new  NaoEncontradoExcepition("Não encontrammos esse reserva no banco de dados");
		}		
		var convertido = biblioteca.get();
		
		if(convertido.getDatareservafim().isAfter(dataRecebida)) {
			throw new  NaoEncontradoExcepition("A data informada é menor que a data do fim da reserva!");
		}
		
		convertido = servico.finalizarReserva(convertido);
		convertido.setDatadevolucao(dataRecebida);
				
		servico.save(convertido);
		resposta.setObjeto(convertido.convertaEntidadeParaDTO());
		
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());	
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	

	/**
	 * Obtem todas reservas atrasadas de entrega conforme o intervalo de datas.
	 * @param diasAconsiderar
	 * @return
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@GetMapping(value = "/dataInicial={dataInicial}&datafinal={dataFinal}")
	@ApiOperation(value = "Obtém todos os registros de livros em poder dos alunos com atraso conforme o intervalo de datas")
	public ResponseEntity<Resposta<List<BibliotecaDTO>>> obterTodosAtrasadosIntervadoDeDatas(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicial, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFinal) throws NaoEncontradoExcepition{
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<List<BibliotecaDTO>> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		var dataIni = UtilApi.converterLocalDateParaLocalDateTime(dataInicial);
		var dataFim = UtilApi.converterLocalDateParaLocalDateTime(dataFinal);
		
		if(dataIni.isAfter(dataFim)) {
			throw new NaoEncontradoExcepition("A data inicial não pode ser maior que a data final");
		}
		
		
		var listaComAtrasos = servico.devolveListaAtrasadosPorIntervaloDeDatas(servico.findAll(), dataIni, dataFim);
		if(listaComAtrasos.isEmpty() || listaComAtrasos.size() <= 0) {
			throw new NaoEncontradoExcepition("Não contem reservas em atraso para esse intervalo de datas");	
		}
		
		resposta.setObjeto(BibliotecaModelo.novaInstancia().criarListaBibliotecaDTO(listaComAtrasos));
		
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
		
	} 
	
}
