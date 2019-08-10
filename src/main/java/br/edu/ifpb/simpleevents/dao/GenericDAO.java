package br.edu.ifpb.simpleevents.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.edu.ifpb.simpleevents.entity.Especialidade;
import br.edu.ifpb.simpleevents.entity.pattern.singleton.LogSingleton;

public class GenericDAO<T, PK extends Serializable> implements Persistent<T, PK>, Serializable{
	
	private static final long serialVersionUID = 1L;

	protected Class<T> entityClass;


	public GenericDAO() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Inject
	protected EntityManager entityManager;


	@Override
	@Transactional
	public T create(T object) {
		this.entityManager.persist(object);
		LogSingleton.getInstance().escrever("Create - " + entityClass.getSimpleName());
		return object;
	}	

	@Override
	public List<T> read() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("select e from " + entityClass.getSimpleName() +" e");
		return (List<T>) query.getResultList();
	}

	@Override
	public T read(Long id) {
		return this.entityManager.find(entityClass, id);
	}

	@Override
	@Transactional
	public T update(T object) {
		LogSingleton.getInstance().escrever("Update - " + entityClass.getSimpleName());
		return this.entityManager.merge(object);
	}

	@Override
	@Transactional
	public void delete(T object) {
		object = this.entityManager.merge(object);
		LogSingleton.getInstance().escrever("Delete - " + entityClass.getSimpleName());
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
