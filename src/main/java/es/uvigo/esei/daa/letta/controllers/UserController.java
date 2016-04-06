package es.uvigo.esei.daa.letta.controllers;

import java.util.List;

import es.uvigo.esei.daa.letta.DAO.DAO;
import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.DAO.UserDAO;
import es.uvigo.esei.daa.letta.entities.Entity;
import es.uvigo.esei.daa.letta.entities.User;

public class UserController implements Controller{
	private DAO dao;
	
	public UserController(){
		this.dao = new UserDAO();
	}
	
	
	public String list() throws DAOException{
		String list = "{'users':{";
		List<Entity> users = this.dao.list();
		if(!users.isEmpty()){
			for(Entity user: users){
				list += "{'login':'" + ((User)user).getLogin() + "'},";
			}
			list = list.substring(0, list.length()-1);
		}
		list += "}}";
		return list;
	}

	@Override
	public String get(Object id) throws DAOException{
		User user = (User)this.dao.get((String)id);
		return "{'login':'" + user.getLogin() + "'}";
	}

	
	public String modify(String login, String password) throws DAOException{
		User user = (User)this.dao.get(login);
		user.setPassword(password);
		this.dao.modify(user);
		return "{'login':'" + user.getLogin() + "'}";
	}

	
	public String add(String login, String password) throws DAOException{
		User user = (User)((UserDAO)this.dao).add(login, password);
		return "{'login':'" + user.getLogin() + "'}";
	}

	@Override
	public void delete(Object id) throws DAOException{
		this.dao.delete((String)id);
	}
	
}
