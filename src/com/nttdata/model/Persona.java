package com.nttdata.model;

public class Persona {

	private String nome;

	public Persona(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Sono un'istanza di Persona e mi chiamo " + nome;
	}

}
