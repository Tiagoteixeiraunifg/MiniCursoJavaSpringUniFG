package com.minicursoadsfg.minicursoadsfg.dominio.FabricaExcecoes;

public class NaoEncontradoExcepition extends Exception {
	
	private static final long serialVersionUID = -1L;
	
	public NaoEncontradoExcepition(){
		super();
	}
	
	public NaoEncontradoExcepition(String msg){
		super(msg);
	}
	
	public NaoEncontradoExcepition(String msg, Throwable cause){
		super(msg, cause);
	}
}
