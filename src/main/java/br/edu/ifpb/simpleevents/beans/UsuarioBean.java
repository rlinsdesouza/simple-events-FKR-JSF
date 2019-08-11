package br.edu.ifpb.simpleevents.beans;

import br.edu.ifpb.simpleevents.controller.ParticipanteController;
import br.edu.ifpb.simpleevents.entity.User;
import br.edu.ifpb.simpleevents.security.PasswordUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean extends GenericBean implements Serializable {

    @Inject
    private ParticipanteController participanteController;

    private User usuario;

    @PostConstruct
    public void init () {
        User usuario = (User) this.getFlash("usuario");
        if (usuario != null)
            this.usuario = usuario;
        else
            this.usuario = new User();
    }

    public User getUsuario() {
        return this.usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String save () {
        this.usuario.setSenha(PasswordUtil.encryptMD5(this.usuario.getSenha()));
        this.participanteController.save(this.usuario);
        return "/index.xhtml?faces-redirect=true";
    }
}


