package es.uvigo.esei.daa.letta.rest;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.controllers.UserController;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
	private final static Logger LOG = Logger.getLogger(UsersResource.class.getName());

    private final UserController userController;


    public UsersResource() {
        this(new UserController());
    }

    // Needed for testing purposes
    UsersResource(UserController controller) {
        this.userController = controller;
    }

    @GET
    @Path("/{login}")
    public Response get(
            @PathParam("login") String login
    ) {
        try {
            final String user = this.userController.get(login);

            return Response.ok(user).build();
        } catch (IllegalArgumentException | NullPointerException ex){
        	LOG.log(Level.FINE, "Invalid user login in get method", ex);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting a person", e);
            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }


    @GET
    public Response list() {
        try {
            return Response.ok(this.userController.list()).build();
        } catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error listing users", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    @POST
    public Response add(
            @FormParam("login") String login,
            @FormParam("password") String password
    ) {
        try {
            String user = this.userController.add(login, password);

            return Response.ok(user).build();
        } catch (IllegalArgumentException | NullPointerException ex) {
			LOG.log(Level.SEVERE, "Error adding a person", ex);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error adding a person", e);
            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{login}")
    public Response modify(
            @PathParam("login") String login,
            @FormParam("password") String password
    ) {
        try {
            String user = this.userController.modify(login, password );

            return Response.ok(user).build();
        } catch (NullPointerException | IllegalArgumentException ex) {
			LOG.log(Level.SEVERE, "Error modifying a person", ex);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();

        } catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error modifying a person", e);
            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{login}")
    public Response delete(
            @PathParam("login") String login
    ) {
        try {
            this.userController.delete(login);

            return Response.ok().build();
        } catch (IllegalArgumentException | NullPointerException ex) {
			LOG.log(Level.SEVERE, "Error getting a pet", ex);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting a pet", e);
            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }
}