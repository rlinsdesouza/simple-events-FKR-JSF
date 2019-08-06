package br.edu.ifpb.simpleevents.beans;

import br.edu.ifpb.simpleevents.controller.ParticipanteController;
import br.edu.ifpb.simpleevents.dao.ParticipanteDAO;
import br.edu.ifpb.simpleevents.entity.User;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class UsuarioBean implements Serializable {

    @Inject
    private ParticipanteController participanteController;

    private User usuario;

    @PostConstruct
    public void init () {
        this.usuario = new User();
    }

    public User getUsuario() {
        return this.usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String save () {
        this.participanteController.create(this.usuario);
        System.out.println(this.participanteController.read());
        return "index.xhtml?faces-redirect=true";
    }
}


