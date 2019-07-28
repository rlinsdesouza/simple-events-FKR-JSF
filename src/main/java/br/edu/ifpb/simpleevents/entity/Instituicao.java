package br.edu.ifpb.simpleevents.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;

public class Instituicao implements Participante {
	
	@Column(unique=true)
	private String email;
	
	private String senha;
	
	@OneToMany
	private List<Participante> participantes;

	public Instituicao(String email, String senha) {
		this.email = email;
		this.senha = senha;
		this.participantes = new ArrayList<>();
	}
	
	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public int getCount() {
		int resultado = 0;			
		for (Participante p: this.participantes)
			resultado += p.getCount();
		return resultado;
		
	}

	public void add (Participante p) {
		this.participantes.add(p);
	}
	
	public void remove (Participante p) {
		this.participantes.remove(p);
	}

	@Override
	public String getSenha() {
		return this.senha;
	}

}
