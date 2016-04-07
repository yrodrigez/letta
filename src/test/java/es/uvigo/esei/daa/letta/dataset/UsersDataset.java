package es.uvigo.esei.daa.letta.dataset;

import es.uvigo.esei.daa.letta.entities.User;

public class UsersDataset {
	
	public static User[] users(){
		return new User[]{
				new User("user1"),
				new User("user2"),
				new User("user3"),
				new User("user4"),
				new User("user5")
		};
	}
}
