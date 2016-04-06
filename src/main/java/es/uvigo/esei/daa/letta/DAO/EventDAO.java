package es.uvigo.esei.daa.letta.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import es.uvigo.esei.daa.letta.entities.Entity;
import es.uvigo.esei.daa.letta.entities.Event;
import es.uvigo.esei.daa.letta.entities.User;

public class EventDAO extends DAO {
	
	public Entity add(String title, String description, String place, int num_assistants, Date start, Date end, String user_id)
	throws DAOException, IllegalArgumentException {
		if (title == null || description == null ||
				place == null || num_assistants < 1 ||
				start == null || end == null || user_id == null ||
				title.length() > 100 || description.length() > 1000 ||
				place.length() > 500 || start.getTime() < System.currentTimeMillis() ||
				end.getTime() < start.getTime() || user_id.length() > 20) {
			throw new IllegalArgumentException("the arguments are wrong");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO event VALUES(null, ?, ?, ?, ?, ?, ?, ?)";
			
			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, title);
				statement.setString(2, description);
				statement.setString(3, place);
				statement.setInt(4, num_assistants);
				statement.setDate(5, new java.sql.Date(start.getTime()));
				statement.setDate(6, new java.sql.Date(end.getTime()));
				statement.setString(7, user_id);
				
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Event(resultKeys.getInt(1), title, description, place, start, end, num_assistants, user_id);
						} else {
							throw new SQLException("Error adding a event");
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
		if (entity == null || ((Event)entity).getId() < 1 ||
				((Event)entity).getTitle() == null || ((Event)entity).getDescription() == null ||
				((Event)entity).getPlace() == null || ((Event)entity).getNum_assistants() < 1 ||
				((Event)entity).getStart() == null || ((Event)entity).getEnd() == null ||
				((Event)entity).getUser_id() == null || ((Event)entity).getTitle().length() > 100 ||
				((Event)entity).getDescription().length() > 1000 || ((Event)entity).getPlace().length() > 500 ||
				((Event)entity).getStart().getTime() < System.currentTimeMillis() || ((Event)entity).getEnd().getTime() < ((Event)entity).getStart().getTime() ||
				((Event)entity).getUser_id().length() > 20) {
			throw new IllegalArgumentException("event is wrong formed");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "UPDATE event SET title = ?, description = ?, place = ?, num_assistants = ?, start = ?, end = ?, user_id = ? WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, ((Event)entity).getTitle());
				statement.setString(2, ((Event)entity).getDescription());
				statement.setString(3, ((Event)entity).getPlace());
				statement.setInt(4, ((Event)entity).getNum_assistants());
				statement.setDate(5, new java.sql.Date(((Event)entity).getStart().getTime()));
				statement.setDate(6, new java.sql.Date(((Event)entity).getEnd().getTime()));
				statement.setString(7, ((Event)entity).getUser_id());
				statement.setInt(8, ((Event)entity).getId());
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("fields can't be null");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException();
		}

	}

	@Override
	protected String getTableName() {
		return "event";
	}

	@Override
	protected Entity rowToEntity(ResultSet result) throws SQLException {
		int id = result.getInt("id");
		String title = result.getString("title");
		String description = result.getString("description");
		String place = result.getString("place");
		Date start = result.getDate("start");
		Date end = result.getDate("end");
		int num_assistants = result.getInt("num_assistants");
		String user_id = result.getString("user_id");
		return new Event(id, title, description, place, start, end, num_assistants, user_id);
	}

	@Override
	protected String getPrimaryKeyFieldName() {
		return "id";
	}

	@Override
	protected void setPrimaryKeyValue(PreparedStatement statement, Object id, int index) throws SQLException {
		statement.setInt(index, (Integer)id);

	}

}
