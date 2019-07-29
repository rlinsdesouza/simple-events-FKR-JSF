package br.edu.ifpb.simpleevents.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
//@Table(name = "tb_instituicao")
public class Instituicao extends Participante {
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Participante> participantes ;

	public Instituicao(String email, String senha) {
		super(email, senha);
		this.participantes = new ArrayList<>();
	}
	
	public Instituicao() {
		super();
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
	
}
