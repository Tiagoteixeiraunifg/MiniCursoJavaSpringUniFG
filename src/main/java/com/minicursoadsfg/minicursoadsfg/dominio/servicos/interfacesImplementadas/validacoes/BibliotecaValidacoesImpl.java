package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfacesImplementadas.validacoes;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.validacoes.IBibliotecaValidacoes;


@Component
public class BibliotecaValidacoesImpl implements IBibliotecaValidacoes{

	@Override
	public MultiValueMap<String, String> validaReserva(BibliotecaModelo biblioteca) {
		MultiValueMap<String, String> resultado = new LinkedMultiValueMap<>();
		
		/*Validando se existe Aluno*/
		if(biblioteca.getAluno().estahPresente()) {
			resultado.add("ALUNO", "ALUNO NÃO INSERIDO");
		}
		return resultado;
	}


	
}
