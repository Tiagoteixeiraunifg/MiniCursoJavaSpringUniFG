package com.minicursoadsfg.minicursoadsfg.dominio.servicos.validacoes;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;

@Component
public interface IBibliotecaValidacoes {
	
	public MultiValueMap<String,String> validaReserva(BibliotecaModelo biblioteca);

}
