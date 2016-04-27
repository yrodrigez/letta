package es.uvigo.esei.daa.letta.controllers;

import java.util.List;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.entities.Entity;

public interface Controller <E extends Entity> {
	public List<E> list() throws DAOException;
	public E get(String id) throws DAOException;
	public void delete(String id) throws DAOException;
}
