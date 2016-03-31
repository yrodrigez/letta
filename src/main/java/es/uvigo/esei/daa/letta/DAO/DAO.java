package es.uvigo.esei.daa.letta.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import es.uvigo.esei.daa.letta.entities.Entity;


public abstract class DAO {
	private final static String JNDI_NAME = "java:/comp/env/jdbc/letta";
	
	private DataSource dataSource;
	
	public DAO() {
		Context initContext;
		try {
			initContext = new InitialContext();
			
			this.dataSource = (DataSource) initContext.lookup(JNDI_NAME);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	public Connection getConnection() throws SQLException{
		return this.dataSource.getConnection();
	}
	
	public List<Entity> list() throws DAOException{
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM " + getTableName() + ";";
			try(final PreparedStatement statement = conn.prepareStatement(query)){
				try (final ResultSet result = statement.executeQuery()) {
					List<Entity> entities = new LinkedList<Entity>();
					while (result.next()) {
						entities.add(rowToEntity(result));
					}
					return entities;
				}
			}
		} catch(SQLException e){
			System.out.println(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}
	
	public Entity get(Object id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM " + getTableName() + " WHERE " + getPrimaryKeyFieldName() +" = ?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				setPrimaryKeyValue(statement, id, 1);
				
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid primary key");
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException(e);
		}
	}
	
	public void delete(Object id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKeyFieldName() +" = ?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				setPrimaryKeyValue(statement, id, 1);
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("Invalid primary key");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DAOException(e);
		}
		
	}
	
	public abstract void modify(Entity entity)
	throws DAOException, IllegalArgumentException;
	
	protected abstract String getTableName();
	
	protected abstract Entity rowToEntity(ResultSet result);
	
	protected abstract String getPrimaryKeyFieldName();
	
	protected abstract void setPrimaryKeyValue(PreparedStatement statement, Object id, int index);
	
}