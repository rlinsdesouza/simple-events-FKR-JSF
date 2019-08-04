package br.edu.ifpb.simpleevents.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.EventoDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.entity.Evento;

public class EventoController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EventoDAO eventoDAO;

	public ArrayList<Evento> consultar() {
		ArrayList<Evento> eventos = eventoDAO.read();
		return eventos;
	}

	@Transactional
	public Evento cadastrar(Evento evento) {
		if(evento.getId() == null) {
			eventoDAO.create(evento);
		}else {
			eventoDAO.update(evento);
		}
		return evento;
	}
	
	@Transactional
	public boolean deletar(Evento evento) {
		boolean deletou = false;
		Evento espec = eventoDAO.read(evento.getId());
		eventoDAO.delete(espec);
		return deletou;
	}
	

}
