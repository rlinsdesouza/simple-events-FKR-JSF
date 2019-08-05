package br.edu.ifpb.simpleevents.dao;

import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.simpleevents.entity.Evento;
import br.edu.ifpb.simpleevents.entity.StatusEvento;
import br.edu.ifpb.simpleevents.entity.pattern.composite.ParticipanteComposite;

public class EventoDAO extends GenericDAO<Evento, Long> {

	private static final long serialVersionUID = 1L;

	public List<Evento> findByDono (ParticipanteComposite dono) {
		try {
			Query query = this.entityManager.createQuery("SELECT e FROM Evento e WHERE e.dono= :dono");
			query.setParameter("dono", dono);
			return (List<Evento>) query.getResultList();
		} catch (Exception e) {
			return null;
		}		
	}
	
	public List<Evento> findByStatus(StatusEvento status) {
		try {
			Query query = this.entityManager.createQuery("SELECT e FROM Evento e WHERE e.status= :status");
			query.setParameter("status", status);
			return (List<Evento>) query.getResultList();
		} catch (Exception e) {
			return null;
		}	
	}
	
	public List<Evento> findByName(StatusEvento nome) {
		try {
			Query query = this.entityManager.createQuery("select e from Evento e left join e.vagas v left join v.especialidade f "
					+ "where lower(e.descricao) like lower(concat('%',:nome,'%'))"
					+ "or lower(e.dono.nome) like lower(concat('%',:nome,'%')) "
					+ "or lower(f.nome) like lower(concat('%',:nome,'%')) "
					);
			query.setParameter("nome", nome);
			return (List<Evento>) query.getResultList();
		} catch (Exception e) {
			return null;
		}	
	}
	
//	
//	@Query("select e from Evento e left join e.vagas v left join v.especialidade f "
//			+ "where lower(e.descricao) like lower(concat('%',:nome,'%'))"
//			+ "or lower(e.dono.nome) like lower(concat('%',:nome,'%')) "
//			+ "or lower(f.nome) like lower(concat('%',:nome,'%')) "

//+ "and e.status.name() like '%ABERTO%'"



}
