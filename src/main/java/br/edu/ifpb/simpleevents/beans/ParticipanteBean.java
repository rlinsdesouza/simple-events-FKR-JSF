package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.controller.ParticipanteController;
import br.edu.ifpb.simpleevents.entity.Instituicao;
import br.edu.ifpb.simpleevents.entity.User;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

@Named
@ViewScoped
public class ParticipanteBean extends GenericBean implements Serializable {

    @Inject
    private ParticipanteController participanteController;

    private ParticipanteComposite participante;

    private List<ParticipanteComposite> participantes;

	private FacesContext facesContext;

    
    public void setParticipante(ParticipanteComposite participante) {
        this.participante = participante;
    }

    public ParticipanteComposite getParticipante() {
        return participante;
    }

    public List<ParticipanteComposite> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteComposite> participantes) {
        this.participantes = participantes;
    }

    public String editar(ParticipanteComposite participante) {
        if(participante instanceof User) {
            this.setFlash("usuario", (User) participante);
            return "/participante/user.xhtml?faces-redirect=true";
        } else {
            this.setFlash("instituicao", (Instituicao) participante);
            return "/participante/instituicao.xhtml?faces-redirect=true";
        }
    }

    public String deletar(ParticipanteComposite participante) {
        this.participanteController.delete(participante);
        this.participantes = this.participanteController.findAll();
        return "/participante/list.xhtml?faces-redirect=true";
    }

	@PostConstruct
	public void init(){
		this.participantes = this.participanteController.findAll();
		this.facesContext = FacesContext.getCurrentInstance();
	}

	public String getInstanceOfParticipante () {
		String type = facesContext.getExternalContext().getRequestParameterMap().get("type");
		if(type.equalsIgnoreCase("instituicao"))
			return "/WEB-INF/facelets/participante/instituicao.xhtml";
		return "/WEB-INF/facelets/participante/user.xhtml";
	}


	public String save () {
		this.participanteController.save(this.participante);
		return "participante/list.xhtml";
	}

	public String edit (ParticipanteComposite participante) {
		this.participante = participante;
//		this.setFlash("participante", participante);
		return "participante/form?faces-redirect=true";
	}
}
