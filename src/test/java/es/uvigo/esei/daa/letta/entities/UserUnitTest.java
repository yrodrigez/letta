package es.uvigo.esei.daa.letta.entities;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserUnitTest {
	
	@Test
	public void testUserString(){
		String username = "username";
		User u= new User(username);
		
		assertThat(u.getLogin(), is(equalTo(username)));
	}
	
	@Test
	public void testSetLogin(){
		String username = "username";
		
		User u= new User(username);
		u.setLogin("nameuser");
		
		assertThat(u.getLogin(), is(equalTo("nameuser")));
	}

	@Test
	public void testEqualsObject() {
		final User userA = new User("user1");
		final User userB = new User("user1");
		
		assertTrue(userA.equals(userB));
	}

	@Test(expected = NullPointerException.class)
	public void testSetInvalidNullLogin(){
		User u = new User("un_user");

		u.setLogin(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidLongLogin() {
		User u = new User("un_user");
		String invalidLogin = "un_login_muy_largo_para_que_pete";

		u.setLogin(invalidLogin);

	}
	
	@Test(expected = NullPointerException.class )
	public void testUserInvalidNullLogin(){
		new User(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserInvalidLongLogin(){
		new User("asdasdasdasdasdasdasdasdasdasdasdasd");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserInvalidEmptyLogin(){
		new User("");
	}

	@Test
	public void testUserNullLoginReturnFalse() {
		String username = "username";
		User u = new User(username);

		assertFalse(u.equals(null));
	}

	@Test
	public void testUserDifferentLoginReturnFalse() {
		String username = "username";
		User u = new User(username);
		User anotherUser = new User("anotherUser");

		assertFalse(u.equals(anotherUser));
	}

}
