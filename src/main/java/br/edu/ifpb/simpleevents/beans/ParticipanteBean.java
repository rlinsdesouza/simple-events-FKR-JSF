package br.edu.ifpb.simpleevents.beans;

import br.edu.ifpb.simpleevents.controller.ParticipanteController;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
public class ParticipanteBean implements Serializable {

	@Inject
	private ParticipanteController participanteController;

	private ParticipanteComposite participante;

	private List<ParticipanteComposite> participantes;

	private FacesContext facesContext;

	@PostConstruct
	public void init(){
		this.participantes = this.participanteController.read();
		this.facesContext = FacesContext.getCurrentInstance();
	}

	public String getInstanceOfParticipante () {
		String type = facesContext.getExternalContext().getRequestParameterMap().get("type");
		if(type.equalsIgnoreCase("instituicao"))
			return "/WEB-INF/facelets/participante/instituicao.xhtml";
		return "/WEB-INF/facelets/participante/user.xhtml";
	}

	public void setParticipante (ParticipanteComposite p) {
		this.participante = p;
	}

	public ParticipanteComposite getParticipante () {
		return this.participante;
	}

	public void setParticipantes(List<ParticipanteComposite> p) {
		this.participantes = p;
	}

	public List<ParticipanteComposite> getParticipantes () {
		return this.participantes;
	}






	public String save () {
		this.participanteController.create(this.participante);
		return "participante/list.xhtml";
	}

	public String edit (ParticipanteComposite participante) {
		this.participante = participante;
//		this.setFlash("participante", participante);
		return "participante/form?faces-redirect=true";
	}
}
