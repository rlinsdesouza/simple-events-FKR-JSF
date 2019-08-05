package br.edu.ifpb.simpleevents.dao;

import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.simpleevents.entity.Especialidade;

public class EspecialidadeDAO extends GenericDAO<Especialidade, Long> {

	private static final long serialVersionUID = 1L;


	public List<Especialidade> read() {
		Query q = entityManager.createQuery("select e from Especialidade e");
		return q.getResultList();
	}

}
