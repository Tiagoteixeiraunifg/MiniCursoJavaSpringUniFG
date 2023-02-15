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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.minicursoadsfg.minicursoadsfg.api.respostas.Resposta;
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.AutorDTO;
import com.minicursoadsfg.minicursoadsfg.comum.UtilApi;
import com.minicursoadsfg.minicursoadsfg.dominio.fabricaExcecoes.NaoAtualizadoException;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.AutorModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.IAutorServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@RestController
@CrossOrigin(origins = "${front.baseurl}")
@RequestMapping(value = "/api/v1/autor", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Operações com Autores dos Livros")
public class AutorControle {
	
	
	private IAutorServico _servico;
	private UtilApi _utilApi;
	
	
	public AutorControle(IAutorServico _servico, UtilApi _utilApi) {
		this._servico = _servico;
		this._utilApi = _utilApi;
	}
	
	/**
	 * Cadastra novo autor
	 * @param autorDTO
	 * @param resultado
	 * @return
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Cadastra um novo autor.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resposta<AutorDTO>> cadastreAutor(@Valid @RequestBody AutorDTO autorDTO, BindingResult resultado){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<AutorDTO> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if (resultado.hasErrors()) {
			resultado.getAllErrors().stream().forEach(error -> resposta.adicionarMensagenErroNaListaResposta(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}
		
		var convertidoEmModelo = autorDTO.convertaDtoParaModelo();
		var resultadoRepositorio =  _servico.save(convertidoEmModelo.preparaCadastroNovo());
		resposta.setObjeto(resultadoRepositorio.convertaModeloParaDto());
		
		
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	
	
	/**
	 * Atualiza um novo autor
	 * @param autorDTO
	 * @param resultado
	 * @return
	 * @throws NaoAtualizadoException
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Atualiza um autor.")
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resposta<AutorDTO>> atualizarAutor(@Valid @RequestBody AutorDTO autorDTO, BindingResult resultado) throws NaoAtualizadoException{
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<AutorDTO> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if (resultado.hasErrors()) {
			resultado.getAllErrors().stream().forEach(error -> resposta.adicionarMensagenErroNaListaResposta(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}
		
		var autor = _servico.findById(autorDTO.getId_autor());
		if(!autor.isPresent()) {
			throw new  NaoAtualizadoException("Autor não localizado na base de dados");
		}
		
		var convertidoEmModelo = autorDTO.convertaDtoParaModelo();
		var resultadoRepositorio =  _servico.save(convertidoEmModelo.preparaCadastroNovo());
		resposta.setObjeto(resultadoRepositorio.convertaModeloParaDto());
		
		
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	
	/**
	 * Obtem todos os autores
	 * @return
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Obter todos os Autores.")
	@GetMapping
	public ResponseEntity<Resposta<List<AutorDTO>>> obterTodosAutores(){
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<List<AutorDTO>> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		resposta.setObjeto(AutorModelo.novaInstancia().criarListaAutorDTO(_servico.findAll()));
		
		if (resposta.getObjeto().size() < 1) {
			headers.add("INFORMACAO", "NÃO ENCONTROU RESULTADOS");
			return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
		}
			
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	
	
	/**
	 * deleta um autor pelo código fornecido
	 * @param idAutor
	 * @return
	 * @throws NaoAtualizadoException
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Deleta um  Autor.")
	@DeleteMapping(value = "/{idAutor}")
	public ResponseEntity<Void> deletaAutorPeloId(@RequestParam Integer idAutor) throws NaoAtualizadoException{
		
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if(!_servico.findById(idAutor).isPresent()) {
			throw new NaoAtualizadoException("Autor não localizado");
		}
		
		_servico.deleteById(idAutor);
		return ResponseEntity.noContent().build();
	}
	
}
