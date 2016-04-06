package es.uvigo.esei.daa.letta.controllers;

import es.uvigo.esei.daa.letta.DAO.DAOException;

public interface Controller {
	public String list() throws DAOException;
	public String get(Object id) throws DAOException;
	public void delete(Object id) throws DAOException;
}
