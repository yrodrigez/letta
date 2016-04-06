package es.uvigo.esei.daa.letta.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserUnitTest {
	
	@Test
	public void testUserStringString(){
		String username = "username";
		String password = "12345678912345678912345678912345";
		User u= new User(username, password);
		
		assertThat(u.getLogin(), is(equalTo(username)));
		assertThat(u.getPassword(), is(equalTo(password)));
	}
	
	@Test
	public void testSetLogin(){
		String username = "username";
		String password = "12345678912345678912345678912345";
		
		User u= new User(username, password);
		u.setLogin("nameuser");
		
		assertThat(u.getLogin(), is(equalTo("nameuser")));
		assertThat(u.getPassword(), is(equalTo(password)));
	}
	
	@Test
	public void testSetPassword(){
		String username = "username";
		String password = "12345678912345678912345678912345";
		
		User u= new User(username, password);
		u.setPassword("12345678912345678912345678912349");
		
		assertThat(u.getLogin(), is(equalTo(username)));
		assertThat(u.getPassword(), is(equalTo("12345678912345678912345678912349")));
	}

	@Test
	public void testEqualsObject() {
		final User userA = new User("user1", "12345678912345678912345678912345");
		final User userB = new User("user1", "12345678912345678912345678912349");
		
		assertTrue(userA.equals(userB));
	}
	
	@Test(expected = NullPointerException.class )
	public void testUserStringNull(){
		new User("user", null);
	}
	
	@Test(expected = NullPointerException.class )
	public void testUserNullString(){
		new User(null,"12345678912345678912345678912345");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserInvalidLongLogin(){
		new User("asdasdasdasdasdasdasdasdasdasdasdasd","12345678912345678912345678912345");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserInvalidEmptyLogin(){
		new User("","12345678912345678912345678912345");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserInvalidShortPassword(){
		new User("u", "1234");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserInvalidLongPassword(){
		new User("u", "1234444444444444444444444444444444444444444444444444444444");
	}

}
