package br.edu.ifpb.simpleevents.dao;

import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.simpleevents.entity.CandidatoVaga;
import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

public class CandidatoVagaDAO extends GenericDAO<CandidatoVaga, Integer> {

	private static final long serialVersionUID = 1L;
	
	public List<CandidatoVaga> findByCandidato (ParticipanteComposite candidato) {
		try {
			Query query = this.entityManager.createQuery("SELECT c FROM CandidatoVaga c WHERE c.candidato= :candidato");
			query.setParameter("candidato", candidato);
			return (List<CandidatoVaga>) query.getResultList();
		} catch (Exception e) {
			return null;
		}		
	}
	
	public List<CandidatoVaga> findByEvento (Evento evento) {
		try {
			Query query = this.entityManager.createQuery("SELECT c FROM CandidatoVaga c join c.vaga v join v.evento "
					+ "WHERE v.evento = :evento");
			query.setParameter("evento", evento);
			return (List<CandidatoVaga>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

}
