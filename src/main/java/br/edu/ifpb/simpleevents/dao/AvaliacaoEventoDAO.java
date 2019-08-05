package br.edu.ifpb.simpleevents.dao;

import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.simpleevents.entity.AvaliacaoEvento;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

public class AvaliacaoEventoDAO extends GenericDAO<AvaliacaoEvento, Long>{

	private static final long serialVersionUID = 1L;

	public List<AvaliacaoEvento> findByParticipante (ParticipanteComposite participante) {
		try {
			Query query = this.entityManager.createQuery("SELECT a FROM AvaliacaoEvento a WHERE a.participante= :participante");
			query.setParameter("participante", participante);
			return (List<AvaliacaoEvento>) query.getResultList();
		} catch (Exception e) {
			return null;
		}		
	}

	
}
