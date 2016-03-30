package es.uvigo.esei.daa.letta.controllers;

public interface Controller {

	public String list();
	public String get(String id);
	public String modify(String entity);
	public void delete(String id);
	
}
