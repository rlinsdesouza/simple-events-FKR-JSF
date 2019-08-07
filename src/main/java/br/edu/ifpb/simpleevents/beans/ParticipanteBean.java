package br.edu.ifpb.simpleevents.beans;

import br.edu.ifpb.simpleevents.controller.ParticipanteController;
import br.edu.ifpb.simpleevents.entity.Instituicao;
import br.edu.ifpb.simpleevents.entity.User;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ParticipanteBean extends GenericBean implements Serializable {

    @Inject
    private ParticipanteController participanteController;

    private ParticipanteComposite participante;

    private List<ParticipanteComposite> participantes;

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
            return "user.xhtml?faces-redirect=true";
        } else {
            this.setFlash("instituicao", (Instituicao) participante);
            return "instituicao.xhtml?faces-redirect=true";
        }
    }

    public String deletar(ParticipanteComposite participante) {
        this.participanteController.delete(participante);
        this.participantes = this.participanteController.findAll();
        return "list-participante.xhtml?faces-redirect=true";
    }

    @PostConstruct
    public void init () {
        this.participantes = this.participanteController.findAll();
    }
}
