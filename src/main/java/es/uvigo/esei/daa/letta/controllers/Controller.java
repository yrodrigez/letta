package es.uvigo.esei.daa.letta.controllers;

import java.util.List;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.entities.Entity;

public interface Controller<E extends Entity> {
	public List<E> list() throws DAOException;
	public E get(Object id) throws DAOException;
	public void delete(Object id) throws DAOException;
}
