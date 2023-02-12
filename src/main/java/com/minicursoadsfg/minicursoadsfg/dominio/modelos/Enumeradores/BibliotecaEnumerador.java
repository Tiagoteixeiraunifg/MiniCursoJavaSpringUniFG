package com.minicursoadsfg.minicursoadsfg.dominio.modelos.Enumeradores;



public enum BibliotecaEnumerador {
	FECHADA("FECHADA"), ABERTA("ABERTA");
	
	private String nome;
	
	private BibliotecaEnumerador(String nome){
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
