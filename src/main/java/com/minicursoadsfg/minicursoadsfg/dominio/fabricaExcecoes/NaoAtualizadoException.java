package com.minicursoadsfg.minicursoadsfg.dominio.fabricaExcecoes;

public class NaoAtualizadoException extends Exception {

	private static final long serialVersionUID = -1L;
	
	public NaoAtualizadoException(){
		super();
	}
	
	public NaoAtualizadoException(String msg){
		super(msg);
	}
	
	public NaoAtualizadoException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
