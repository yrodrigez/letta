package es.uvigo.esei.daa.letta.entities;
import java.util.Date;

public class Event {
	private int id;
	private String title;
	private String description;
	private String place;
	private Date start;
	private Date end;
	private int num_assistants;
	private String user_id;
	
	public Event(int id, String title, String description, String place, Date start, Date end, int num_assistants,
			String user_id) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.place = place;
		this.start = start;
		this.end = end;
		this.num_assistants = num_assistants;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getNum_assistants() {
		return num_assistants;
	}

	public void setNum_assistants(int num_assistants) {
		this.num_assistants = num_assistants;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	

}
