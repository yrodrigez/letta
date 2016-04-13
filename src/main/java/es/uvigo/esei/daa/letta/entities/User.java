package es.uvigo.esei.daa.letta.entities;

public class User implements Entity {
	private String login;
	
	User(){}
	
	public User(String login) {
		this.setLogin(login);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		if(login == null)
			throw new NullPointerException("Name can't be null");
		else if(login.length() > 20 || login.length() < 1)
			throw new IllegalArgumentException("Name length must be less than 20 characters");
		this.login = login;
	}

  public boolean equals(User u){
    if(u == null)
      return false;
    if(u.getLogin() != this.login)
      return false;
    return true;
  }
}
