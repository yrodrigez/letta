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
	@Test(expected = NullPointerException.class)
	public void testSetNullTitle() {

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

			e.setTitle(null);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = NullPointerException.class)
	public void testSetNullDescription() {

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

			e.setDescription(null);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = NullPointerException.class)
	public void testSetNullPlace() {

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

			e.setPlace(null);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = NullPointerException.class)
	public void testSetNullUser_id() {

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

			e.setUser_id(null);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalId() {

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

			e.setId(0);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalShortTitle() {

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

			e.setTitle("");

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalLongTitle() {

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

			e.setTitle("aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjk");


		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalShortDescription() {

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

			e.setDescription("");


		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalLongDescription() {

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

			e.setDescription("aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjk");


		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalShortPlace() {

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

			e.setPlace("");

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalLongPlace() {

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

			e.setPlace("aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjk");


		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalNum_assistants() {

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

			e.setNum_assistants(0);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalShortUser_id() {

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

			e.setUser_id("");

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test (expected = IllegalArgumentException.class)
	public void testSetIllegalLongUser_id() {

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

			e.setUser_id("a1s1s1s1s1s1s1s1s1s12");

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}


	@Test
	public void testSetTitle() {

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

			e.setTitle("El Quixote");

			assertThat(e.getId(), is(equalTo(id)));
			assertThat(e.getDescription(), is(equalTo(description)));
			assertThat(e.getTitle(), is(equalTo("El Quixote")));
			assertThat(e.getPlace(), is(equalTo(place)));
			assertThat(e.getStart().getTime(), is(equalTo(start.getTime())));
			assertThat(e.getEnd().getTime(), is(equalTo(end.getTime())));
			assertThat(e.getNum_assistants(), is(equalTo(num_assistants)));
			assertThat(e.getUser_id(), is(equalTo(user_id)));



		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testSetDescription() {

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

			e.setDescription("El Quixote");

			assertThat(e.getId(), is(equalTo(id)));
			assertThat(e.getDescription(), is(equalTo("El Quixote")));
			assertThat(e.getTitle(), is(equalTo(title)));
			assertThat(e.getPlace(), is(equalTo(place)));
			assertThat(e.getStart().getTime(), is(equalTo(start.getTime())));
			assertThat(e.getEnd().getTime(), is(equalTo(end.getTime())));
			assertThat(e.getNum_assistants(), is(equalTo(num_assistants)));
			assertThat(e.getUser_id(), is(equalTo(user_id)));



		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test
	public void testSetPlace() {

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

			e.setPlace("El Quixote");

			assertThat(e.getId(), is(equalTo(id)));
			assertThat(e.getDescription(), is(equalTo(description)));
			assertThat(e.getTitle(), is(equalTo(title)));
			assertThat(e.getPlace(), is(equalTo("El Quixote")));
			assertThat(e.getStart().getTime(), is(equalTo(start.getTime())));
			assertThat(e.getEnd().getTime(), is(equalTo(end.getTime())));
			assertThat(e.getNum_assistants(), is(equalTo(num_assistants)));
			assertThat(e.getUser_id(), is(equalTo(user_id)));



		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test
	public void testSetNum_assistants() {

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

			e.setNum_assistants(100);

			assertThat(e.getId(), is(equalTo(id)));
			assertThat(e.getDescription(), is(equalTo(description)));
			assertThat(e.getTitle(), is(equalTo(title)));
			assertThat(e.getPlace(), is(equalTo(place)));
			assertThat(e.getStart().getTime(), is(equalTo(start.getTime())));
			assertThat(e.getEnd().getTime(), is(equalTo(end.getTime())));
			assertThat(e.getNum_assistants(), is(equalTo(100)));
			assertThat(e.getUser_id(), is(equalTo(user_id)));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testSetStart() {

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

			Date newDate = formatter.parse("2016-06-01 16:00:00") ;
			e.setStart(newDate);

			assertThat(e.getId(), is(equalTo(id)));
			assertThat(e.getDescription(), is(equalTo(description)));
			assertThat(e.getTitle(), is(equalTo(title)));
			assertThat(e.getPlace(), is(equalTo(place)));
			assertThat(e.getStart().getTime(), is(equalTo(newDate.getTime())));
			assertThat(e.getEnd().getTime(), is(equalTo(end.getTime())));
			assertThat(e.getNum_assistants(), is(equalTo(num_assistants)));
			assertThat(e.getUser_id(), is(equalTo(user_id)));



		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test
	public void testSetEnd() {

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

			Date newDate = formatter.parse("2016-06-01 16:00:00") ;
			e.setEnd(newDate);

			assertThat(e.getId(), is(equalTo(id)));
			assertThat(e.getDescription(), is(equalTo(description)));
			assertThat(e.getTitle(), is(equalTo(title)));
			assertThat(e.getPlace(), is(equalTo(place)));
			assertThat(e.getStart().getTime(), is(equalTo(start.getTime())));
			assertThat(e.getEnd().getTime(), is(equalTo(newDate.getTime())));
			assertThat(e.getNum_assistants(), is(equalTo(num_assistants)));
			assertThat(e.getUser_id(), is(equalTo(user_id)));



		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test
	public void testSetUser_id() {

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

			e.setUser_id("molo");

			assertThat(e.getId(), is(equalTo(id)));
			assertThat(e.getDescription(), is(equalTo(description)));
			assertThat(e.getTitle(), is(equalTo(title)));
			assertThat(e.getPlace(), is(equalTo(place)));
			assertThat(e.getStart().getTime(), is(equalTo(start.getTime())));
			assertThat(e.getEnd().getTime(), is(equalTo(end.getTime())));
			assertThat(e.getNum_assistants(), is(equalTo(num_assistants)));
			assertThat(e.getUser_id(), is(equalTo("molo")));



		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	@Test(expected = NullPointerException.class)
	public void testSetNullStart() {
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

			e.setStart(null);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = NullPointerException.class)
	public void testSetNullEnd() {

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

			e.setEnd(null);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}


}
