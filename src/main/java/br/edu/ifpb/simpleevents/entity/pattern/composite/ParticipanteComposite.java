package br.edu.ifpb.simpleevents.entity.pattern.composite;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@Table(name = "tb_participante")
public abstract class ParticipanteComposite {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Long id;
	
	@Column(unique=true)
	protected String email;
	
//	@Size(min = 8, message = "a senha deve conter no minino 8 caracteres")
	protected String senha;
	
	public ParticipanteComposite(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}
	
	public ParticipanteComposite() {
		
	}
	
	public abstract int getCount();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getSenha() {
		return this.senha;
	}
}
