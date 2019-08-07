package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.entity.Especialidade;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.Vaga;
import br.edu.ifpb.simpleevents.facade.EspecialidadeController;
import br.edu.ifpb.simpleevents.facade.EventoController;

@Named(value = "analiseCandidatos")
@ViewScoped
public class AnaliseCandidatosBean extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EventoController evtcontrol;

	
	@PostConstruct
	private void init() {
		Evento evento = (Evento) this.getFlash("evento");
		if (evento != null) {
			this.evento = evento;
		} else {
			this.evento = new Evento();
		}
	}
	
}
