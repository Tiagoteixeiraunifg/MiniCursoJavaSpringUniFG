package com.minicursoadsfg.minicursoadsfg.api.v1.controles;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import com.minicursoadsfg.minicursoadsfg.api.respostas.Resposta;
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.AlunoDTO;
import com.minicursoadsfg.minicursoadsfg.comum.UtilApi;
import com.minicursoadsfg.minicursoadsfg.dominio.FabricaExcecoes.NaoEncontradoExcepition;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AlunoModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.IAlunoServico;

import io.swagger.annotations.Api;


/**
 * @author tiago teixeira
 *
 */


@RestController
@CrossOrigin(origins = "${front.baseurl}")
@RequestMapping(value = "/api/v1/aluno", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Operações com alunos")
public class AlunoControle {

	
	
	private IAlunoServico _alunoServico;
	private UtilApi _utilApi;
	
	public AlunoControle(IAlunoServico _alunoServico, UtilApi _utilApi) {
		this._alunoServico = _alunoServico;
		this._utilApi = _utilApi;
	}
	
	
	/**
	 * Cadastra um novo aluno
	 * @param alunoDTO
	 * @param resultado
	 * @return
	 * @throws NaoEncontradoExcepition 
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Cadastra um novo aluno.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resposta<AlunoDTO>> cadastreAluno(@Valid @RequestBody AlunoDTO alunoDTO, BindingResult resultado) throws NaoEncontradoExcepition{
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<AlunoDTO> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if (resultado.hasErrors()) {
			resultado.getAllErrors().stream().forEach(error -> resposta.adicionarMensagenErroNaListaResposta(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}
		
		if(_alunoServico.findByEmail(alunoDTO.getEmail()).isPresent()) {
			throw new NaoEncontradoExcepition("Aluno ja cadastrado com a ID informada!");
		}
		
		var convertidoEmModelo = alunoDTO.convertaDtoParaModelo();
		var resultadoRepositorio =  _alunoServico.save(convertidoEmModelo.preparaCadastroNovo());
		resposta.setObjeto(resultadoRepositorio.convertaModeloParaDto());
		
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	
	
	/**
	 * Obtem todos os alunos registrados
	 * @return
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@GetMapping
	@ApiOperation(value = "Obtém todos os registros de alunos")
	public ResponseEntity<Resposta<List<AlunoDTO>>> obterTodos(){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<List<AlunoDTO>> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		resposta.setObjeto(AlunoModelo.novaInstancia().criarListaAlunoDTO(_alunoServico.findAll()));
		
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
		
	} 
	
	/**
	 * Atuaiza um aluno na base de dados
	 * @param alunoDTO
	 * @param resultado
	 * @return
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Atualiza um aluno existente na base.")
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resposta<AlunoDTO>> atualizeAluno(@Valid @RequestBody AlunoDTO alunoDTO, BindingResult resultado){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<AlunoDTO> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if (resultado.hasErrors()) {
			resultado.getAllErrors().stream().forEach(error -> resposta.adicionarMensagenErroNaListaResposta(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}
				
		if(!_alunoServico.findById(alunoDTO.getId_aluno()).isPresent()) {
			resposta.adicionarMensagenErroNaResposta("Aluno não encontrado na base de dados");
			return ResponseEntity.badRequest().body(resposta);
		}
		
		var convertidoEmModelo = alunoDTO.convertaDtoParaModelo();
		var resultadoRepositorio =  _alunoServico.save(convertidoEmModelo.preparaCadastroNovo());
		resposta.setObjeto(resultadoRepositorio.convertaModeloParaDto());
		headers.add("Aluno: "+resposta.getObjeto().getNome()+"", "ATUALIZADO");
		

		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	
	/**
	 * Deleta um aluno na base de dados
	 * @param id_aluno
	 * @return
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Deleta um Aluno cadastrado passando o ID.")
	@DeleteMapping(value = "/{id_aluno}")
	public ResponseEntity<Resposta<Void>> delete(@PathVariable Integer id_aluno) throws NaoEncontradoExcepition {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<Void> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if (!_alunoServico.findById(id_aluno).isPresent()) {
			throw new NaoEncontradoExcepition("Aluno nao encontrado com a ID informada!");
		}
		
		_alunoServico.deleteById(id_aluno);
		resposta.adicionarMensagenErroNaResposta("Aluno deletado corretamente");
		
		return new ResponseEntity<>(resposta, headers, HttpStatus.NO_CONTENT);
	}

}
