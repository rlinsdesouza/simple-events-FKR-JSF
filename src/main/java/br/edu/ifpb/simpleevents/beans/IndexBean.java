package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifpb.simpleevents.controller.EventoController;
import br.edu.ifpb.simpleevents.controller.IndexController;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;
import br.edu.ifpb.simpleevents.facade.LoginFacade;

@Named(value = "index")
@ViewScoped
public class IndexBean extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Evento> eventos;
	private ParticipanteComposite userLogin;
	private String pesquisaNome;

	@Inject
	private IndexController controller;
	
	@Inject
	private EventoController eventoController;
	
	@PostConstruct
	private void init() {
		this.eventos = (List<Evento>) this.getFlash("eventos");
		if (this.eventos == null) {
			this.eventos = controller.getEventos();
		}
		this.userLogin = LoginFacade.getParticipanteLogado();
	}
	

	public String showindex() {
    	HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    	if(LoginFacade.isAuthenticated()) {
        	response.addCookie(new Cookie("lastUser",this.userLogin.getEmail()));
        }
    	return "/WEB-INF/facelets/evento/listInCards.xhtml";
    }
	
	public String showNavBar() {
		if(LoginFacade.isAuthenticated()) {
        	return "/WEB-INF/facelets/navbar/navbarLoggedIn.xhtml";
        }else {
        	return "/WEB-INF/facelets/navbar/navbarLoggedOut.xhtml";
        }
	}
	
	

//    @GetMapping("/login")
//    public String login(Model model, @CookieValue(value = "lastUser", defaultValue="nenhum") String lastUser) {
//    	if(!lastUser.equals("nenhum")) {
//    		model.addAttribute("lastUser",lastUser);	
//    	}
//    	return "login"; // <<< Retorna a página de login
//    }
//    
    public String pesquisar() {
    	this.eventos = eventoController.findByName(this.pesquisaNome);
    	return null;
    }

	// get and setters

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public ParticipanteComposite getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(ParticipanteComposite userLogin) {
		this.userLogin = userLogin;
	}

	public String getPesquisaNome() {
		return pesquisaNome;
	}

	public void setPesquisaNome(String pequisaNome) {
		this.pesquisaNome = pequisaNome;
	}
	
}
