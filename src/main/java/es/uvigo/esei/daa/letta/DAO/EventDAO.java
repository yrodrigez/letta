package es.uvigo.esei.daa.letta.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.daa.letta.entities.Event;

public class EventDAO extends DAO<Event> {
	private final static Logger LOG = Logger.getLogger(EventDAO.class.getName());
	
	public Event add(String title, String description, String place, int num_assistants, Date start, Date end, String user_id)
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
			LOG.log(Level.SEVERE, "Error adding an entity", e);
			throw new DAOException(e);
		}
		
	}

	public void modify(Event event) throws DAOException, IllegalArgumentException {
		if (event == null || event.getId() < 1 ||
				event.getTitle() == null || event.getDescription() == null ||
				event.getPlace() == null || event.getNum_assistants() < 1 ||
				event.getStart() == null || event.getEnd() == null ||
				event.getUser_id() == null || event.getTitle().length() > 100 ||
				event.getDescription().length() > 1000 || event.getPlace().length() > 500 ||
				event.getStart().getTime() < System.currentTimeMillis() || event.getEnd().getTime() < event.getStart().getTime() ||
				event.getUser_id().length() > 20) {
			throw new IllegalArgumentException("Event is wrong formed");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "UPDATE event SET title = ?, description = ?, place = ?, num_assistants = ?, start = ?, end = ?, user_id = ? WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, event.getTitle());
				statement.setString(2, event.getDescription());
				statement.setString(3, event.getPlace());
				statement.setInt(4, event.getNum_assistants());
				statement.setDate(5, new java.sql.Date(event.getStart().getTime()));
				statement.setDate(6, new java.sql.Date(event.getEnd().getTime()));
				statement.setString(7, event.getUser_id());
				statement.setInt(8, event.getId());
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("fields can't be null");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error modifying an event", e);
			throw new DAOException();
		}

	}

	@Override
	protected String getTableName() {
		return "event";
	}

	@Override
	protected Event rowToEntity(ResultSet result) throws SQLException {
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

	public List<Event> getFeatured() throws DAOException{
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM " + getTableName() + " WHERE start > NOW() LIMIT 5;";
			try(final PreparedStatement statement = conn.prepareStatement(query)){
				try (final ResultSet result = statement.executeQuery()) {
					List<Event> events = new LinkedList<>();
					while (result.next()) {
						events.add(rowToEntity(result));
					}
					return events;
				}
			}
		} catch(SQLException e){
			LOG.log(Level.SEVERE, "Error listing entities", e);
			throw new DAOException(e.getMessage());
		}
	}

	public List<Event> getPopular() throws DAOException{
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM " + getTableName() + " WHERE start > NOW() ORDER BY start LIMIT 10;";
			try(final PreparedStatement statement = conn.prepareStatement(query)){
				try (final ResultSet result = statement.executeQuery()) {
					List<Event> events = new LinkedList<>();
					while (result.next()) {
						events.add(rowToEntity(result));
					}
					return events;
				}
			}
		} catch(SQLException e){
			LOG.log(Level.SEVERE, "Error listing entities", e);
			throw new DAOException(e.getMessage());
		}
	}

}
