package br.edu.ifpb.simpleevents.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

import java.util.List;

public class ParticipanteDAO extends GenericDAO<ParticipanteComposite, Long> {

	private static final long serialVersionUID = 1L;

	public ParticipanteComposite findByEmail (String email) {
		try {
			Query q = this.entityManager.createQuery("SELECT p FROM Participante p WHERE p.email = :email");
			q.setParameter("email", email);
			return (ParticipanteComposite) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	public List<ParticipanteComposite> getParticipantes() {
		Query q = this.entityManager.createQuery("select x from Participante x");
		return q.getResultList();
	}


}
