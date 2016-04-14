package es.uvigo.esei.daa.letta.controllers;

import java.util.List;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.DAO.UserDAO;
import es.uvigo.esei.daa.letta.entities.User;

public class UserController implements Controller<User>{
	private UserDAO dao;
	
	public UserController(){
		this.dao = new UserDAO();
	}
	
	public List<User> list() throws DAOException{
		return this.dao.list();
	}

	@Override
	public User get(String id) throws DAOException{
		return this.dao.get(id);
	}

	
	public User modify(String login, String password) throws DAOException{
		User user = this.dao.get(login);
		this.dao.modify(user, password);
		return user;
	}

	
	public User add(String login, String password) throws DAOException{
		return this.dao.add(login, password);
	}

	@Override
	public void delete(String id) throws DAOException{
		this.dao.delete(id);
	}
	
}
