package es.uvigo.esei.daa.letta.controllers;

import java.util.Date;
import java.util.List;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.DAO.EventDAO;
import es.uvigo.esei.daa.letta.entities.Event;

public class EventController implements Controller<Event>{

	private EventDAO dao;
	
	public EventController(){
		this.dao = new EventDAO();
	}
	@Override
	public List<Event> list() throws DAOException {
		return this.dao.list();
	}

	@Override
	public Event get(Object id) throws DAOException{
		return this.dao.get((Integer)id);
	}

	@Override
	public void delete(Object id) throws DAOException{
		this.dao.delete((Integer)id);
	}
	
	public Event modify(int id, String title, String description, String place,
		int num_assistants, Date start, Date end, String user_id)
	throws DAOException {
		Event event = (Event)this.dao.get(id);
		event.setTitle(title);
		event.setDescription(description);
		event.setPlace(place);
		event.setNum_assistants(num_assistants);
		event.setStart(start);
		event.setEnd(end);
		event.setUser_id(user_id);
		this.dao.modify(event);
		return event;
	}

	
	public Event add(String title, String description, String place, int num_assistants,
		Date start, Date end, String user_id)
	throws DAOException{
		return this.dao.add(title, description, place, num_assistants, start, end, user_id);
	}

}
