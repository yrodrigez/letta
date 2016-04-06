package es.uvigo.esei.daa.letta.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.uvigo.esei.daa.letta.entities.Entity;
import es.uvigo.esei.daa.letta.entities.User;

public class UserDAO extends DAO{
	
	public Entity add(String login, String password) throws DAOException, IllegalArgumentException {
		if(login == null)
			throw new NullPointerException("Login can't be null");
		if(password == null)
			throw new NullPointerException("Password can't be null");
		if(	login.length() > 20 || login == "")
			throw new IllegalArgumentException("Login length must be between 1 and 20 characters");
		if(password.length() != 32)
			throw new IllegalArgumentException("Password must be 32 characters long");
		
		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO user VALUES(?, ?)";
			
			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, login);
				statement.setString(2, password);
				
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new User(login, password);
						} else {
							throw new SQLException("Error adding a user");
						}
					}
				} else {
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException(e);
		}
		
	}

	@Override
	public void modify(Entity entity) throws DAOException, IllegalArgumentException {
		if (entity == null) {
			throw new NullPointerException("user can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "UPDATE user SET password=? WHERE login=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(2, ((User)entity).getLogin());
				statement.setString(1, ((User)entity).getPassword());
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("password can't be null");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException();
		}
		
	}

	@Override
	protected String getTableName() {
		return "user";
	}

	@Override
	protected Entity rowToEntity(ResultSet result) throws SQLException{
		String login = result.getString("login");
		String password = result.getString("password");
		return new User(login, password);
	}

	@Override
	protected String getPrimaryKeyFieldName() {
		return "login";
	}

	@Override
	protected void setPrimaryKeyValue(PreparedStatement statement, Object id, int index)
	throws SQLException{
		statement.setString(index, (String)id);
		
	}

}
