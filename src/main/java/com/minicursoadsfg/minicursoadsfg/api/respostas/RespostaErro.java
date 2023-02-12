package com.minicursoadsfg.minicursoadsfg.api.respostas;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class RespostaErro {
	
	public static RespostaErro novaInstancia() {
		return new RespostaErro();
	}
	
	@NotNull(message="Timestamp cannot be null")
	private LocalDateTime dataImpressa;
	
	@NotNull(message="Details cannot be null")
    private String detalhes;

}
