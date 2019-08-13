package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.controller.CandidatoVagaController;
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

	private ParticipanteComposite userLog;
	private List<CandidatoVaga> candidaturas;
	private Map<Long,String> mapAvaliacoes;
	private Integer rating;
	
	
//	@PostConstruct
//	private void init() {
////		List<CandidatoVaga> candidaturas = (List<CandidatoVaga>) this.getFlash("candidaturas");
////		if (candidaturas != null) {
////			this.candidaturas = candidaturas;
////		} else {
//			this.candidaturas = new ArrayList<CandidatoVaga>();
////		}
//	}
	
  public String listMeusTrabalhos() {
      this.userLog = LoginFacade.getParticipanteLogado();
      this.candidaturas = candidaturaControl.getCandidaturas(userLog);
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
  
  public String desistir(Long id) {
	  try {
		if(candidaturaControl.delete(id)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","Desistiu com sucesso!"));
			return null;
		}
	} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getMessage()));
		e.printStackTrace();
		return null;
	};
	return null;	  
  }

  public String avaliar(CandidatoVaga candidatura) {
	  candidaturaControl.avaliarEvento(candidatura, candidatura.getVaga().getEvento(), this.rating);
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Avaliado!", "Avaliado com sucesso!");
      FacesContext.getCurrentInstance().addMessage(null, message);
	  return null;
  }  
  
  
//get and setters
	
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
	
	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Map<Long, String> getMapAvaliacoes() {
		return mapAvaliacoes;
	}
	
	public void setMapAvaliacoes(Map<Long, String> mapAvaliacoes) {
		this.mapAvaliacoes = mapAvaliacoes;
	}
  
   
}
