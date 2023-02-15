package com.minicursoadsfg.minicursoadsfg.dominio.fabricaExcecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerErrorException;

import com.minicursoadsfg.minicursoadsfg.api.respostas.Resposta;



@ControllerAdvice
public class ExcecaoHandler<T> {

	/**
	 * Autor: Tiago Teixeira
	 * Exceção com resposta 403, quando ouver erro de validação ou atualizacao nos end-poits PUT 
	 * @param excecao
	 * @return
	 */
	@ExceptionHandler(value = { NaoAtualizadoException.class })
    protected ResponseEntity<Resposta<T>> HandleNaoAtualizadoException(NaoAtualizadoException excecao) {
		
		Resposta<T> resposta = new Resposta<>();
		resposta.adicionarMensagenErroNaResposta(excecao.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
    }
	
	
	/**
	 * Autor: Tiago Teixeira
	 * Exceção com resposta 404, quando nao for encontrado o recurso nos end-poits GET 
	 * @param excecao
	 * @return
	 */
	@ExceptionHandler(value = { NaoEncontradoExcepition.class })
    protected ResponseEntity<Resposta<T>> HandleNaoEncontradoExcepition(NaoEncontradoExcepition excecao) {
		
		Resposta<T> resposta = new Resposta<>();
		resposta.adicionarMensagenErroNaResposta(excecao.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }
	
	
	/**
	 * Autor: Tiago Teixeira
	 * Exceção com resposta 500, erro no servidor genérico.
	 * @param excecao
	 * @return
	 */
	@ExceptionHandler(value = { ServerErrorException.class })
    protected ResponseEntity<Resposta<T>> handleAPIException(ServerErrorException exception) {
		
		Resposta<T> resposta = new Resposta<>();
		resposta.adicionarMensagenErroNaResposta(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}
