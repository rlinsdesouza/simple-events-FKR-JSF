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

@Named(value = "trabalhos")
@ViewScoped
public class MeusTrabalhosBean extends GenericBean implements Serializable {
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

  public String listmyevents() {
//      ModelAndView modelList = new ModelAndView("evento/list");
//      User usuarioLogado = userDAO.findByEmail(auth.getName());
//      modelList.addObject("userLog", usuarioLogado);
//      modelList.addObject("eventos", eventoDAO.findByDono(usuarioLogado));
//      modelList.addObject("eventosVagas", converterListasEventos());
      return "WEB-INF/facelets/evento/meustrabalhos.xhtml";
  }

}
