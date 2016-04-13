package es.uvigo.esei.daa.letta.entities;
import java.util.Date;

public class Event implements Entity{
	private int id;
	private String title;
	private String description;
	private String place;
	private Date start;
	private Date end;
	private int num_assistants;
	private String user_id;
	
	Event(){}
	
	public Event(int id, String title, String description, String place, Date start, Date end, int num_assistants,
			String user_id) {
		this.setId(id);
		this.setTitle(title);
		this.setDescription(description);
		this.setPlace(place);
		this.setStart(start);
		this.setEnd(end);
		this.setNum_assistants(num_assistants);
		this.setUser_id(user_id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(id < 1)
			throw new IllegalArgumentException("Id must be greater than 0");
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(title == null)
			throw new NullPointerException("Title can't be null");
		else if(title.length() > 100 || title.length() < 1)
			throw new IllegalArgumentException("Title must be at leas 1 character long and a maximum of 100 characters long");
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description == null)
			throw new NullPointerException("Description can't be null");
		else if(description.length() > 1000 || description.length() < 1)
			throw new IllegalArgumentException("Description must be at leas 1 character long and a maximum of 1000 characters long");
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		if(place == null)
			throw new NullPointerException("Place can't be null");
		else if(place.length() > 500 || place.length() < 1)
			throw new IllegalArgumentException("Place must be at leas 1 character long and a maximum of 500 characters long");
		this.place = place;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		if(start == null)
			throw new NullPointerException("Start date can't be null");
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		if(end == null)
			throw new NullPointerException("End date can't be null");
		this.end = end;
	}

	public int getNum_assistants() {
		return num_assistants;
	}

	public void setNum_assistants(int num_assistants) {
		if(num_assistants < 1)
			throw new IllegalArgumentException("The number of assistants must be greater than 0");
		this.num_assistants = num_assistants;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		if(user_id == null)
			throw new NullPointerException("User id can't be null");
		else if(user_id.length() > 20 || user_id.length() < 1)
			throw new IllegalArgumentException("User id length must be less than 20 characters");
		
		this.user_id = user_id;
	}
	
	

}
