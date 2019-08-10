package br.edu.ifpb.simpleevents.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.EspecialidadeDAO;
import br.edu.ifpb.simpleevents.dao.EventoDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.dao.UserDAO;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.StatusEvento;

public class IndexController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	public EventoDAO eventoDAO;
	@Inject
	public UserDAO userDAO;
	@Inject
	public EspecialidadeDAO especDAO;
	
	@Transactional
	public List<Evento> getEventos() {
		return eventoDAO.findByStatus(StatusEvento.ABERTO);
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


}
