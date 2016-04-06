package es.uvigo.esei.daa.letta.entities;

public class User implements Entity {
	private String login;
	private String password;
	public User(String login, String password) {
		this.setLogin(login);
		this.setPassword(password);
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		if(login == null)
			throw new NullPointerException("Name can't be null");
		else if(login.length() > 20 || login.length() < 1)
			throw new IllegalArgumentException("Name lenght must be less than 20 characters");
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(password == null)
			throw new NullPointerException("Password can't be null");
		else if(password.length() != 32)
			throw new IllegalArgumentException("Password lenght must be 32 characters");
		this.password = password;
	}
	
	public boolean equals(User u){
		if(u == null)
			return false;
		if(u.getLogin() != this.login)
			return false;
		return true;
	}
}
