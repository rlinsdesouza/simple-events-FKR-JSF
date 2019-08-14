package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.HashMap;
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
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.facade.LoginFacade;

@Named(value = "eventosList")
@ViewScoped
public class EventoListBean extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EventoController evtcontrol;

	private Map<Long, Map<Long, String>> eventosVagas;
	private List<Evento> eventos;
	private ParticipanteComposite userLogin;
	
	@PostConstruct
	public void init() {
		this.userLogin = LoginFacade.getParticipanteLogado();
	}

	public String list() {
		if(LoginFacade.isAuthenticated() && userLogin.getAdmin()) {
			this.eventos = evtcontrol.findAll();
			this.eventosVagas = converterListasEventos();

			return "/WEB-INF/facelets/evento/list.xhtml";
		}else {
			return "/WEB-INF/facelets/gabaritos/forbidden.xhtml";
		}	
	}

	public String listByParticipante() {
		if (LoginFacade.isAuthenticated())
			this.eventos = evtcontrol.findByDono(LoginFacade.getParticipanteLogado());

		this.eventosVagas = converterListasEventos();

		return "/WEB-INF/facelets/evento/list.xhtml";
	}

	private Map<Long, Map<Long, String>> converterListasEventos() {
		Map<Long, Map<Long, String>> eventosVagas = new HashMap<>();
		for (Evento e : evtcontrol.findAll()) {
			eventosVagas.put(e.getId(), converterLista(e.getVagas(), true));
		}
		return eventosVagas;
	}

	private Map<Long, String> converterLista(List<Vaga> vagas, boolean aprovado) {

		Map<Long, String> vagasCandidatos = new HashMap<>();
		if (vagas != null) {
			for (Vaga v : vagas) {
				for (CandidatoVaga c : v.getCandidatoVaga()) {
					if (aprovado) {
						if (c.getStatus().name().equals("APROVADO")) {
							vagasCandidatos.put(c.getId(),
									c.getCandidato().getNome() + " - " + v.getEspecialidade().getNome());
						}
					} else {
						vagasCandidatos.put(c.getId(), c.getCandidato().getNome() + " - "
								+ (c.getNotaDesempenho() == -1 ? "Aguardando Avaliação" : c.getNotaDesempenho()));
					}
				}
			}
		}
		return vagasCandidatos;
	}
	
	public String cancelar(Long id) {
		try {
			if (evtcontrol.delete(id) == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","Evento deletado com sucesso!"));
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Apenas cancelado!","Evento so foi cancelado por ja existir candidatos aprovados nele!"));
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Opaaa!",e.getMessage()));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}
		return "/eventos/meuseventos.xhtml?faces-redirect=true";
	}

	// get and setters
	
	

	public Map<Long, Map<Long, String>> getEventosVagas() {
		return eventosVagas;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public void setEventosVagas(Map<Long, Map<Long, String>> eventosVagas) {
		this.eventosVagas = eventosVagas;
	}

}
