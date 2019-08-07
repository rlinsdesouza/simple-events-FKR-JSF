package br.edu.ifpb.simpleevents.dao;

import java.io.Serializable;

import java.util.List;

public interface Persistent<T, PK extends Serializable> {
	public T create(T object);
	public List<T> read();
	public T read(Long id);
	public T update(T object);
	public void delete(T object);
	public void begin();
	public void commit();
	public void rollback();
}
