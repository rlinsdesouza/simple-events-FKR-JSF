package br.edu.ifpb.simpleevents.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProducer {
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("simple-events",PersistenceProperties.get());
	
	@Produces
	@RequestScoped
	public EntityManager createEntityManager () {
		return factory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager entityManager) {
		entityManager.close();
	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return factory;
	}

}
