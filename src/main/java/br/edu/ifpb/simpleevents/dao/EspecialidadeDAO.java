package br.edu.ifpb.simpleevents.dao;

import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.simpleevents.entity.Especialidade;

public class EspecialidadeDAO extends GenericDAO<Especialidade, Long> {

	private static final long serialVersionUID = 1L;

	public Especialidade getByName(String name) {
		Query query = entityManager.createQuery("SELECT e FROM Especialidade e WHERE e.nome = :name");
		query.setParameter("name", name);
		return (Especialidade) query.getSingleResult();
	}

}
