package br.edu.ifpb.simpleevents.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.facade.IndexController;

@Named(value = "index")
@SessionScoped
public class IndexBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Evento> eventos;
	private String teste;

	@Inject
	private IndexController controller;

	public String teste() {
		return null;
	}
	
	public String showindex() {
    	HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    	String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    	if(user != null) {
        	response.addCookie(new Cookie("lastUser",user));
        }
    	this.eventos = controller.getEventos();
    	return "/WEB-INF/facelets/evento/listInCards.xhtml";

    }
	
	public String showNavBar() {
		String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    	if(user != null) {
        	return "/WEB-INF/facelets/fragments/navbarLoggedIn.xhtml";
        }else {
        	return "/WEB-INF/facelets/fragments/navbarLoggedIn.xhtml";
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
//    @GetMapping("/pesquisar")
//    public String pesquisar(Model model,
//    		@RequestParam(value = "nome", required = false) String nome) {
//    	model.addAttribute("eventos",eventoDAO.findByName(nome));
//    	return "index";
//    }

	// get and setters

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public String getTeste() {
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}

}
