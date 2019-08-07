package br.edu.ifpb.simpleevents.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.EventoDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.pattern.singleton.LogSingleton;

public class EventoController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EventoDAO eventoDAO;

	public ArrayList<Evento> consultar() {
		ArrayList<Evento> eventos = (ArrayList<Evento>) eventoDAO.read();
		return eventos;
	}

	@Transactional
	public Evento cadastrar(Evento evento) {
		if(evento.getId() == null) {
			eventoDAO.create(evento);
			LogSingleton.getInstance().escrever("Create - " + evento.getDescricao() );
		}else {
			eventoDAO.update(evento);
			LogSingleton.getInstance().escrever("Update - " + evento.getDescricao() );
		}
		return evento;
	}
	
	@Transactional
	public boolean deletar(Evento evento) {
		boolean deletou = false;
		Evento espec = eventoDAO.read(evento.getId());
		eventoDAO.delete(espec);
		if(deletou)
			LogSingleton.getInstance().escrever("Delete - " + evento.getDescricao() );
		return deletou;
	}
	

}
