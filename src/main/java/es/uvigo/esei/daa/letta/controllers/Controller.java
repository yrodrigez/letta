package es.uvigo.esei.daa.letta.controllers;

public interface Controller {
	public String list();
	public String get(Object id);
	public String delete(Object id);
}
