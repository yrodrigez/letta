package es.uvigo.esei.daa.letta.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class EventUnitTest {
	@Test
	public void testIntStringStringStringDateDateIntString(){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int id = 1;
			String title = "Title1";
			String description = "Description1";
			String place = "Place1";
			Date start = formatter.parse("2016-05-01 16:00:00");
			Date end = formatter.parse("2016-05-01 16:00:01");
			int num_assistants = 10;
			String user_id = "user1";
			
			Event e = new Event(id, title, description, place, start, end, num_assistants, user_id);
			
			assertThat(e.getId(), is(equalTo(id)));
			assertThat(e.getDescription(), is(equalTo(description)));
			assertThat(e.getTitle(), is(equalTo(title)));
			assertThat(e.getPlace(), is(equalTo(place)));
			assertThat(e.getStart().getTime(), is(equalTo(start.getTime())));
			assertThat(e.getEnd().getTime(), is(equalTo(end.getTime())));
			assertThat(e.getNum_assistants(), is(equalTo(num_assistants)));
			assertThat(e.getUser_id(), is(equalTo(user_id)));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
