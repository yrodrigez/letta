package es.uvigo.esei.daa.letta.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Blob;

import es.uvigo.esei.daa.letta.entities.Event;
import es.uvigo.esei.daa.letta.entities.Image;

public class ImageDAO{
	private final static String JNDI_NAME = "java:/comp/env/jdbc/letta";
	private final static Logger LOG = Logger.getLogger(ImageDAO.class.getName());
	
	private DataSource dataSource;
	
	public ImageDAO() {
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
	
	public Image getImage(String tablename, Object id) throws DAOException, IllegalArgumentException{
		try (final Connection conn = this.getConnection()) {
			String primaryKeyFieldName = "";
			if(tablename == "event")
				primaryKeyFieldName = "id";
			else
				throw new IllegalArgumentException();
			final String query = "SELECT img, img_ext FROM " + tablename + " WHERE " + primaryKeyFieldName +" = ?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				if(tablename == "event")
					statement.setInt((Integer)id, 1);
				
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
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
	
	private Image rowToEntity(ResultSet result) throws SQLException{
		String img_ext = result.getString("img_ext");
		Blob b = result.getBlob("img");
		byte[] img = b.getBytes(1, (int)b.length());
		return new Image(img_ext, img);
	}
}
