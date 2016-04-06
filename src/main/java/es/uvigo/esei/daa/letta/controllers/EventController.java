package es.uvigo.esei.daa.letta.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import es.uvigo.esei.daa.letta.DAO.DAO;
import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.DAO.EventDAO;
import es.uvigo.esei.daa.letta.entities.Entity;
import es.uvigo.esei.daa.letta.entities.Event;

public class EventController implements Controller{

	private DAO dao;
	
	public EventController(){
		this.dao = new EventDAO();
	}
	@Override
	public String list() throws DAOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String list = "{'events':{";
		List<Entity> events = this.dao.list();
		if(!events.isEmpty()){
			for(Entity event: events){
				list += "{'id':'" + ((Event)event).getId() + "',"
						+ "'title':'"+ ((Event)event).getTitle() +"',"
						+ "'description':'"+((Event)event).getDescription() + "',"
						+ "'place':'"+((Event)event).getPlace() + "',"
						+ "'num_assistants':'"+((Event)event).getNum_assistants() + "',"
						+ "'start':'"+df.format(((Event)event).getStart()) + "',"
						+ "'end':'"+df.format(((Event)event).getEnd()) + "',"
						+ "'user_id':'"+((Event)event).getUser_id() +"'},";
			}
			list = list.substring(0, list.length()-1);
		}
		list += "}}";
		return list;
	}

	@Override
	public String get(Object id) throws DAOException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Event event = (Event)this.dao.get((Integer)id);
		return "{'id':'" + event.getId() + "',"
		+ "'title':'"+ event.getTitle() +"',"
		+ "'description':'"+event.getDescription() + "',"
		+ "'place':'"+event.getPlace() + "',"
		+ "'num_assistants':'"+event.getNum_assistants() + "',"
		+ "'start':'"+df.format(event.getStart()) + "',"
		+ "'end':'"+df.format(event.getEnd()) + "',"
		+ "'user_id':'"+event.getUser_id() +"'}";
	}

	@Override
	public void delete(Object id) throws DAOException{
		this.dao.delete((Integer)id);
	}
	
	public String modify(
		int id,
		String title,
		String description,
		String place,
		int num_assistants,
		Date start,
		Date end,
		String user_id
	) throws DAOException{
		Event event = (Event)this.dao.get(id);
		event.setTitle(title);
		event.setDescription(description);
		event.setPlace(place);
		event.setNum_assistants(num_assistants);
		event.setStart(start);
		event.setEnd(end);
		event.setUser_id(user_id);
		this.dao.modify(event);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "{'id':'" + event.getId() + "',"
		+ "'title':'"+ event.getTitle() +"',"
		+ "'description':'"+event.getDescription() + "',"
		+ "'place':'"+event.getPlace() + "',"
		+ "'num_assistants':'"+event.getNum_assistants() + "',"
		+ "'start':'"+df.format(event.getStart()) + "',"
		+ "'end':'"+df.format(event.getEnd()) + "',"
		+ "'user_id':'"+event.getUser_id() +"'}";
	}

	
	public String add(
		String title,
		String description,
		String place,
		int num_assistants,
		Date start,
		Date end,
		String user_id
	) throws DAOException{
		Event event = (Event)((EventDAO)this.dao).add(title, description, place, num_assistants, start, end, user_id);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "{'id':'" + event.getId() + "',"
		+ "'title':'"+ event.getTitle() +"',"
		+ "'description':'"+event.getDescription() + "',"
		+ "'place':'"+event.getPlace() + "',"
		+ "'num_assistants':'"+event.getNum_assistants() + "',"
		+ "'start':'"+df.format(event.getStart()) + "',"
		+ "'end':'"+df.format(event.getEnd()) + "',"
		+ "'user_id':'"+event.getUser_id() +"'}";
	}

}
