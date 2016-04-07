package es.uvigo.esei.daa.letta.rest;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.controllers.EventController;

import java.util.Date;
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
public class EventResource {
    private final static Logger LOG = Logger.getLogger(UsersResource.class.getName());

    private final EventController eventsController;


    public EventResource() {
        this(new EventController());
    }

    // Needed for testing purposes
    EventResource(EventController controller) {
        this.eventsController = controller;
    }

    @GET
    @Path("/{id}")
    public Response get(
            @PathParam("id") int id
    ) {
        try {
            final String event = this.eventsController.get(id);

            return Response.ok(event).build();
        } catch (IllegalArgumentException | NullPointerException ex){
            LOG.log(Level.FINE, "Invalid even id in get method", ex);
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
            return Response.ok(this.eventsController.list()).build();
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Error listing events", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    @POST
    public Response add(
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("place") String place,
            @FormParam("num_assistants") int     num_assistants,
            @FormParam("start") Date start,
            @FormParam("end") Date end,
            @FormParam("user_id") String user_id

            ) {
        try {
            String event = this.eventsController.add(
                    title,
                    description,
                    place,
                    num_assistants,
                    start,
                    end,
                    user_id
            );

            return Response.ok(event).build();
        } catch (IllegalArgumentException | NullPointerException ex) {
            LOG.log(Level.SEVERE, "Error adding a event", ex);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Error adding a event", e);
            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response modify(
            @PathParam("id") int id,
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("place") String place,
            @FormParam("num_assistants") int num_assistants,
            @FormParam("start") Date start,
            @FormParam("end") Date end,
            @FormParam("user_id") String user_id
    ) {
        try {
            String event = this.eventsController.modify(
                    id,
                    title,
                    description,
                    place,
                    num_assistants,
                    start,
                    end,
                    user_id
            );

            return Response.ok(event).build();
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
    @Path("/{id}")
    public Response delete(
            @PathParam("id") int id
    ) {
        try {
            this.eventsController.delete(id);

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