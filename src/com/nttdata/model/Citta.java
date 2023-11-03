package com.nttdata.model;

public class Citta {

	private String nome;
	private String regione;

	public Citta(String nome, String regione) {
		this.nome = nome;
		this.regione = regione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	@Override
	public String toString() {
		return nome;
	}

}
