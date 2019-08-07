package br.edu.ifpb.simpleevents.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import br.edu.ifpb.simpleevents.dao.EspecialidadeDAO;
import br.edu.ifpb.simpleevents.dao.Transactional;
import br.edu.ifpb.simpleevents.entity.Especialidade;
import br.edu.ifpb.simpleevents.entity.pattern.singleton.LogSingleton;

public class EspecialidadeController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EspecialidadeDAO especialidadeDAO;

	public ArrayList<Especialidade> consultar() {
		ArrayList<Especialidade> especialidades = (ArrayList<Especialidade>) especialidadeDAO.read();
		return especialidades;
	}

	@Transactional
	public Especialidade cadastrar(Especialidade especialidade) {
		if(especialidade.getId() == null) {
			especialidadeDAO.create(especialidade);
			LogSingleton.getInstance().escrever("Create - " + especialidade.getDescricao() );
		}else {
			especialidadeDAO.update(especialidade);
			LogSingleton.getInstance().escrever("Update - " + especialidade.getDescricao() );
		}
		return especialidade;
	}
	
	@Transactional
	public Boolean deletar(Especialidade especialidade) {
		boolean deletou = false;
		Especialidade espec = especialidadeDAO.read(especialidade.getId());
		especialidadeDAO.delete(espec);
		if(deletou)
			LogSingleton.getInstance().escrever("Delete - " + especialidade.getDescricao() );
		return deletou;
	}
	
}
