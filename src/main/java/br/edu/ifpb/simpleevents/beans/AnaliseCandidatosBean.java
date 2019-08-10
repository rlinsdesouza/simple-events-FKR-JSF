package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.controller.EventoController;
import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.Vaga;

@Named(value = "analiseCandidatos")
@ViewScoped
public class AnaliseCandidatosBean extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EventoController evtcontrol;

	private Evento evento;
	private List<CandidatoVaga> candidaturas;

	@PostConstruct
	private void init() {
		List<CandidatoVaga> candidaturas = (List<CandidatoVaga>) this.getFlash("candidaturas");
		if (candidaturas != null) {
			this.candidaturas = candidaturas;
		} else {
			this.candidaturas = new ArrayList<CandidatoVaga>();
		}
	}

	public String analisarCandidatos() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		System.out.println(params);
		Long id = Long.parseLong(params.get("id"));
		Evento evento = evtcontrol.findById(id);
		for (Vaga vaga : evento.getVagas()) {
			for (CandidatoVaga candidatura : vaga.getCandidatoVaga()) {
				candidaturas.add(candidatura);
			}
		}
		return "/WEB-INF/facelets/evento/analisecandidatos.xhtml";
	}
	
	public String aprovar(String id) {
		Long i = Long.parseUnsignedLong(id);
		try {
			CandidatoVaga candidatura = evtcontrol.aprovarCandidato(i);
			if (candidatura != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","Aprovado com sucesso!"));
				return "/eventos/escolhecandidatos.xhtml?faces-redirect=true&id=" +candidatura.getVaga().getEvento().getId();
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Ops!","Candidato já aprovado no evento ou Vaga lotada!"));
				return "/eventos/escolhecandidatos.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.toString()));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "/index.xhtml?faces-redirect=true";
		}
	}
	
	public String reprovar(String id) {
		Long i = Long.parseUnsignedLong(id);
		try {
			CandidatoVaga candidatura = evtcontrol.reprovarCandidato(i);
			if (candidatura != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","Reprovado com sucesso!"));
				return "/eventos/escolhecandidatos.xhtml?faces-redirect=true&id=" +candidatura.getVaga().getEvento().getId();
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"ops!","nao era pra exibir"));
				return "/eventos/escolhecandidatos.xhtml?faces-redirect=true&id=" +candidatura.getVaga().getEvento().getId();
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getMessage()));
			return "/index.xhtml?faces-redirect=true";
		}
	}

	// get and setters

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<CandidatoVaga> getCandidaturas() {
		return candidaturas;
	}

	public void setCandidaturas(List<CandidatoVaga> candidaturas) {
		this.candidaturas = candidaturas;
	}

}
