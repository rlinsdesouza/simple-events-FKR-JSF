package br.edu.ifpb.simpleevents.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;


@Entity
@DiscriminatorValue("user")
public class User extends ParticipanteComposite {
//implements UserDetails {


//	@NotEmpty(message = "Nome e obrigatorio")
	private String nome;

	//@Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$", message = "Informe um telefone [(83) 98892-1223]")
	private String telefone;
	
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	@Past(message = "A data deve estar no passado")
	private Date datanascimento;
	
	/* rela��o com eventos */
	@OneToMany(mappedBy = "dono", cascade = CascadeType.ALL)
	private List<Evento> eventos = new ArrayList<>();
	
	/* relacao com candidato_vaga */
	@OneToOne
	private CandidatoVaga candidatoVaga;
	
	/* relacao com Avaliacao_evento */
	@OneToOne
	private AvaliacaoEvento avaliacaoEvento;
	
	

	public User() {
	}

	public AvaliacaoEvento getAvaliacaoEvento() {
		return avaliacaoEvento;
	}

	public void setAvaliacaoEvento(AvaliacaoEvento avaliacao) {
		this.avaliacaoEvento = avaliacao;
	}

	public CandidatoVaga getCandidatoVaga() {
		return candidatoVaga;
	}

	public void setCandidatoVaga(CandidatoVaga candidatoVaga) {
		this.candidatoVaga = candidatoVaga;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}

	public void add(Evento ev) {
		this.eventos.add(ev);
	}

	public void remove(Evento ev) {
		this.eventos.remove(ev);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", senha=" + senha
				+ ", datanascimento=" + datanascimento + "]";
	}

	@Override
	public int getCount() {
		return 1;
	}

}
