package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.controller.CandidatoVagaController;
import br.edu.ifpb.simpleevents.controller.EventoController;
import br.edu.ifpb.simpleevents.entity.AvaliacaoEvento;
import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.facade.LoginFacade;

@Named(value = "trabalhos")
@ViewScoped
public class MeusTrabalhosBean extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Inject
	private CandidatoVagaController candidaturaControl;

	private Evento evento;
	private ParticipanteComposite userLog;
	private List<CandidatoVaga> candidaturas;
	private Map<Long,String> mapAvaliacoes;
	
	@PostConstruct
	private void init() {
//		List<CandidatoVaga> candidaturas = (List<CandidatoVaga>) this.getFlash("candidaturas");
//		if (candidaturas != null) {
//			this.candidaturas = candidaturas;
//		} else {
			this.candidaturas = new ArrayList<CandidatoVaga>();
//		}
	}
	
  public String listMeusTrabalhos() {
      this.userLog = LoginFacade.getParticipanteLogado();
      System.out.println(userLog);
      this.candidaturas = candidaturaControl.getCandidaturas(userLog);
      System.out.println(candidaturas);
      List<AvaliacaoEvento> avaliacoes = candidaturaControl.getAvaliacoesPorUsuario(userLog); 
      this.mapAvaliacoes = fazerDicionarioAvaliacoeUsuario(avaliacoes);
		
      return "/WEB-INF/facelets/evento/meustrabalhos.xhtml";
  }
  
  private Map<Long,String> fazerDicionarioAvaliacoeUsuario(List<AvaliacaoEvento> avaliacoesDoUsuario) {
		Map<Long,String> mapAvaliacoes = new HashMap<Long, String>();
		for (AvaliacaoEvento avaliacaoEvento : avaliacoesDoUsuario) {
			mapAvaliacoes.put(avaliacaoEvento.getEvento().getId(), Integer.toString(avaliacaoEvento.getNotaAvaliacaoEvento()));
		}
		return mapAvaliacoes;
  }
  
//get and setters
  
	
	public Evento getEvento() {
		return evento;
	}
	
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public ParticipanteComposite getUserLog() {
		return userLog;
	}
	
	public void setUserLog(ParticipanteComposite userLog) {
		this.userLog = userLog;
	}
	
	public List<CandidatoVaga> getCandidaturas() {
		return candidaturas;
	}
	
	public void setCandidaturas(List<CandidatoVaga> candidaturas) {
		this.candidaturas = candidaturas;
	}
	
	public Map<Long, String> getMapAvaliacoes() {
		return mapAvaliacoes;
	}
	
	public void setMapAvaliacoes(Map<Long, String> mapAvaliacoes) {
		this.mapAvaliacoes = mapAvaliacoes;
	}
  
   
}
