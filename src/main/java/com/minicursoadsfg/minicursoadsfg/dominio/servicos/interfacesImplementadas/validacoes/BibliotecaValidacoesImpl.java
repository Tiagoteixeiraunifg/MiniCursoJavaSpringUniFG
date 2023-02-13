package com.minicursoadsfg.minicursoadsfg.dominio.servicos.interfacesImplementadas.validacoes;
import org.springframework.stereotype.Component;
import com.minicursoadsfg.minicursoadsfg.api.respostas.Resposta;
import com.minicursoadsfg.minicursoadsfg.dominio.modelos.BibliotecaModelo;
import com.minicursoadsfg.minicursoadsfg.dominio.servicos.validacoes.IBibliotecaValidacoes;


@Component
public class BibliotecaValidacoesImpl implements IBibliotecaValidacoes{

	

	@Override
	public Resposta<Void> validaReserva(BibliotecaModelo biblioteca) {
		
		Resposta<Void> resposta = new Resposta<>();
		/*Validando se existe Aluno*/
		if(biblioteca.getAluno().estahPresente()) {
			resposta.adicionarMensagenErroNaListaResposta("Campo aluno não foi inserido");
		}
		
		return resposta;
	}


	
}
