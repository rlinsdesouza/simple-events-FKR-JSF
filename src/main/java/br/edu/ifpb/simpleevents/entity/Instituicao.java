package br.edu.ifpb.simpleevents.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

@Entity
@DiscriminatorValue("instituicao")
public class Instituicao extends ParticipanteComposite {
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<ParticipanteComposite> participantes ;

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
		for (ParticipanteComposite p: this.participantes)
			resultado += p.getCount();
		return resultado;
		
	}

	public void add (ParticipanteComposite p) {
		this.participantes.add(p);
	}
	
	public void remove (ParticipanteComposite p) {
		this.participantes.remove(p);
	}	
	
}
