package com.minicursoadsfg.minicursoadsfg.api.respostas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resposta<T> {
	
	private T objeto;
	private Object data;
	private List<Object> listaErros = new ArrayList<>();
	
	public void adicionarMensagenErroNaResposta(String informacao) {
			var erro = RespostaErro.novaInstancia();
			erro.setDataImpressa(LocalDateTime.now())
			.setDetalhes(informacao);
			setData(erro);
	}
	
	public void adicionarMensagenErroNaListaResposta(String informacao) {
		var erro = RespostaErro.novaInstancia();
		erro.setDataImpressa(LocalDateTime.now())
		.setDetalhes(informacao);
		listaErros.add(erro);
	}
}
