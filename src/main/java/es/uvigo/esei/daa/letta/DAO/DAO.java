package es.uvigo.esei.daa.letta.DAO;

import java.sql.Blob;
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

import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.daa.letta.entities.Entity;
import es.uvigo.esei.daa.letta.entities.Image;


public abstract class DAO<E extends Entity> {
	private final static String JNDI_NAME = "java:/comp/env/jdbc/letta";
	private final static Logger LOG = Logger.getLogger(DAO.class.getName());
	
	private DataSource dataSource;
	
	public DAO() {
		Context initContext;
		try {
			initContext = new InitialContext();
			
			this.dataSource = (DataSource) initContext.lookup(JNDI_NAME);
		} catch (NamingException e) {
			LOG.log(Level.SEVERE, "Error initializing DAO", e);
			throw new RuntimeException(e);
		}
	}
	public Connection getConnection() throws SQLException{
		return this.dataSource.getConnection();
	}
	
	public List<E> list() throws DAOException{
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM " + getTableName() + ";";
			try(final PreparedStatement statement = conn.prepareStatement(query)){
				try (final ResultSet result = statement.executeQuery()) {
					List<E> entities = new LinkedList<>();
					while (result.next()) {
						entities.add(rowToEntity(result));
					}
					return entities;
				}
			}
		} catch(SQLException e){
			LOG.log(Level.SEVERE, "Error listing entities", e);
			throw new DAOException(e.getMessage());
		}
	}
	
	public E get(String id)
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
			LOG.log(Level.SEVERE, "Error getting an " + this.getTableName(), e);
			throw new DAOException(e);
		}
	}
	
	public void delete(String id)
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
			LOG.log(Level.SEVERE, "Error deleting an " + this.getTableName(), e);
			throw new DAOException(e);
		}
		
	}
	
	public Image getImage(String id) throws DAOException, IllegalArgumentException{
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT img, img_ext FROM " + this.getTableName() + " WHERE " + this.getPrimaryKeyFieldName() +" = ?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				setPrimaryKeyValue(statement,id,1);
				
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return imageToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid primary key");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting an image", e);
			throw new DAOException(e);
		}
	}
	
	private Image imageToEntity(ResultSet result) throws SQLException{
		String img_ext = result.getString("img_ext");
		Image.ExtensionTypes ext = null;
		if(img_ext.equals(Image.ExtensionTypes.jpg.toString()))
			ext = Image.ExtensionTypes.jpg;
		else if(img_ext.equals(Image.ExtensionTypes.jpeg.toString()))
			ext = Image.ExtensionTypes.jpeg;
		else if(img_ext.equals(Image.ExtensionTypes.png.toString()))
			ext = Image.ExtensionTypes.png;
		else
			throw new IllegalArgumentException("Image file type not supported");
		Blob b = result.getBlob("img");
		byte[] img = b.getBytes(1, (int)b.length());
		return new Image(ext, img);
	}
	
	protected abstract String getTableName();
	
	protected abstract E rowToEntity(ResultSet result) throws SQLException;
	
	protected abstract String getPrimaryKeyFieldName();
	
	protected abstract void setPrimaryKeyValue(PreparedStatement statement, String id, int index) throws SQLException;


}
