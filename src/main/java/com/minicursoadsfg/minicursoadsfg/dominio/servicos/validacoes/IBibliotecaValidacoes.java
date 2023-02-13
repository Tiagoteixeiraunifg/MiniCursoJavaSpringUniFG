package com.minicursoadsfg.minicursoadsfg.dominio.servicos.validacoes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import com.minicursoadsfg.minicursoadsfg.api.respostas.Resposta;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;

@Component
public interface IBibliotecaValidacoes {
	
	public Resposta<Void> validaReserva(BibliotecaModelo biblioteca);

}
