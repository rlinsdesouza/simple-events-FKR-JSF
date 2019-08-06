package br.edu.ifpb.simpleevents.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GenericDAO<T, PK extends Serializable> implements Persistent<T, PK>, Serializable{
	
	private static final long serialVersionUID = 1L;

	protected Class<T> entityClass;


	public GenericDAO() {
//		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
//		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Inject
	protected EntityManager entityManager;


	@Override
	@Transactional
	public T create(T object) {
		this.entityManager.persist(object);
		return object;
	}	

	@Override
	public List<T> read() {
		System.out.println(entityClass.getSimpleName());
		Query query = entityManager.createQuery("select x from " + entityClass.getSimpleName() + " x");
		return new ArrayList<T>(query.getResultList());
	}

	@Override
	public T read(PK id) {
		return this.entityManager.find(entityClass, id);
	}

	@Override
	@Transactional
	public T update(T object) {
		return this.entityManager.merge(object);
	}

	@Override
	@Transactional
	public void delete(T object) {
		object = this.entityManager.merge(object);
		this.entityManager.remove(object);
	}
	
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public void begin() {
		this.entityManager.getTransaction().begin();
		
	}

	@Override
	public void commit() {
		this.entityManager.flush();
		this.entityManager.getTransaction().commit();
	}

	@Override
	public void rollback() {
		this.entityManager.getTransaction().rollback();		
	}

}
