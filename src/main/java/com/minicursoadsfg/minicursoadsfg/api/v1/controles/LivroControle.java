/**
 * 
 */
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
import com.minicursoadsfg.minicursoadsfg.api.v1.dto.LivroDTO;
import com.minicursoadsfg.minicursoadsfg.comum.UtilApi;
import com.minicursoadsfg.minicursoadsfg.dominio.fabricaExcecoes.NaoEncontradoExcepition;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.LivroModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.IAutorServico;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfaces.ILivroServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author tiago
 *
 */

@RestController
@CrossOrigin(origins = "${front.baseurl}")
@RequestMapping(value = "/api/v1/livro", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Operações com livros")
public class LivroControle {
	

	private ILivroServico _servico;
	private IAutorServico _servicoAutor;
	private UtilApi _utilApi;
	
	public LivroControle(ILivroServico _servico, UtilApi _utilApi, IAutorServico _servicoAutor) {
		this._servico = _servico;
		this._utilApi = _utilApi;
		this._servicoAutor = _servicoAutor;
	}
	
	/**
	 * Cadastra um novo livro com um autor existente
	 * @param livroDTO
	 * @param resultado
	 * @return
	 * @throws NaoEncontradoExcepition
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Cadastra um novo livro.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resposta<LivroDTO>> cadastreLivro(@Valid @RequestBody LivroDTO livroDTO, BindingResult resultado) throws NaoEncontradoExcepition{
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<LivroDTO> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if (resultado.hasErrors()) {
			resultado.getAllErrors().stream().forEach(error -> resposta.adicionarMensagenErroNaListaResposta(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}
		
		if(!livroDTO.getAutor().stream().anyMatch(x -> x.getId_autor() > 0)) {
			resposta.adicionarMensagenErroNaResposta("Erro Autor não informado!");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
		}else {
			for(AutorDTO autor : livroDTO.getAutor()) {
				if(!_servicoAutor.findById(autor.getId_autor()).isPresent()) {
					throw new NaoEncontradoExcepition("Erro autor não cadastrado no banco de dados");
				}
			}	
		}
		
		
		var convertidoEmModelo = livroDTO.convertaDtoParaModelo();
		var resultadoRepositorio =  _servico.save(convertidoEmModelo.preparaCadastroNovo());
		resposta.setObjeto(resultadoRepositorio.convertaEntidadeParaDto());
		
		
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}

	
	
	/**
	 * Obtem todos os livros
	 * @return
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Obter todos os livros.")
	@GetMapping
	public ResponseEntity<Resposta<List<LivroDTO>>> obterTodosLivros() throws NaoEncontradoExcepition{
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<List<LivroDTO>> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		resposta.setObjeto(LivroModelo.novaInstancia().criarListaLivroDTO(_servico.findAll()));
		
		if (resposta.getObjeto().size() < 1) {
			throw new NaoEncontradoExcepition("Não existem livros cadastrados");
		}
			
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	
	
	
	
	/**
	 * Atualiza um registro de um livro 
	 * Deve passar a lista com todos os autores referenciados pra que sejam inseridos novamente.
	 * @param livroDTO
	 * @param resultado
	 * @return
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Atualiza um livro.")
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resposta<LivroDTO>> atualizeLivro(@Valid @RequestBody LivroDTO livroDTO, BindingResult resultado) throws NaoEncontradoExcepition{
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Resposta<LivroDTO> resposta = new Resposta<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		
		if (resultado.hasErrors()) {
			resultado.getAllErrors().stream().forEach(error -> resposta.adicionarMensagenErroNaListaResposta(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(resposta);
		}
		
		if(!livroDTO.getAutor().stream().anyMatch(x -> x.getId_autor() > 0)) {
			resposta.adicionarMensagenErroNaResposta("Erro Autor não encontrado!");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
		}else {
			for(AutorDTO autor : livroDTO.getAutor()) {
				if(!_servicoAutor.findById(autor.getId_autor()).isPresent()) {
					throw new NaoEncontradoExcepition("Erro autor não cadastrado no banco de dados");
				}
			}	
		}
		
		
		var convertidoEmModelo = livroDTO.convertaDtoParaModelo();
		var resultadoRepositorio =  _servico.save(convertidoEmModelo.preparaCadastroNovo());
		resposta.setObjeto(resultadoRepositorio.convertaEntidadeParaDto());
		
		
		return new ResponseEntity<>(resposta, headers, HttpStatus.OK);
	}
	
	/**
	 * Deleta um livro com uma ID
	 * @param id_livro
	 * @return
	 * @throws NaoEncontradoExcepition
	 */
	@CrossOrigin(origins = "${front.baseurl}")
	@ApiOperation(value = "Deleta um livro.")
	@DeleteMapping(value = "/{id_livro}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Resposta<Void>> deleteLivroPeloId(@RequestParam Integer id_livro) throws NaoEncontradoExcepition{
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("VERSAO_API", _utilApi.API_VERSION());
		headers.add("RELEASE_DATA_API", _utilApi.RELEASE_VERSION());
		Resposta<Void> resposta = new Resposta<>();
		
		
		if (!_servico.findById(id_livro).isPresent()) {
			throw new NaoEncontradoExcepition("Livro não encontrado na base de dados!");
		}
		
		_servico.deleteById(id_livro);
		resposta.adicionarMensagenErroNaResposta("Livro deletado corretamente");

				
		return new ResponseEntity<>(resposta, headers, HttpStatus.NO_CONTENT);
		
		
	}
	
}
