package es.uvigo.esei.daa.letta.controllers;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.DAO.ImageDAO;
import es.uvigo.esei.daa.letta.entities.Image;

public class ImageController{
	
	private ImageDAO dao;
	
	public ImageController(){
		this.dao = new ImageDAO();
	}
	
	public Image get(String tablename, Object id) throws DAOException {
		return this.dao.getImage(tablename, id);
	}

}
