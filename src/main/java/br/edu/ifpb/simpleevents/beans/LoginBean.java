package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.simpleevents.beans.GenericBean;
import br.edu.ifpb.simpleevents.controller.LoginController;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.facade.LoginFacade;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean extends GenericBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private String usuario;
	
	private String senha;
	
	private ParticipanteComposite usuarioLogado;
	
	@Inject
	private LoginController loginController;
	
	public String autenticar() {
		String proxView = null;
		if ((usuarioLogado = loginController.isValido(usuario, senha)) != null) {
			this.setValueOf("#{sessionScope.user}", String.class, usuarioLogado.getEmail());
			this.setValueOf("#{sessionScope.username}", String.class, usuarioLogado.getEmail().split("@")[0]);
			LoginFacade.setParticipanteLogado(usuarioLogado);
			proxView = "/index?faces-redirect=true";
		} else {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "Login invalido.");
		}
		
		return proxView;
	}
	
	public String logout() {
		this.invalidateSession();
		LoginFacade.setParticipanteLogado(null);
		return "/login?faces-redirect=true";
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public ParticipanteComposite getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(ParticipanteComposite usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
