package es.uvigo.esei.daa.letta.rest;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.controllers.EventController;
import es.uvigo.esei.daa.letta.controllers.NotLoggedInException;
import es.uvigo.esei.daa.letta.entities.Event;
import es.uvigo.esei.daa.letta.entities.Event.Categories;
import es.uvigo.esei.daa.letta.entities.Image;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/events")
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

    @POST
    @Path("/assist/{eventId}")
    public Response assist(
            @Context HttpServletRequest request,
            @PathParam("eventId") String eventId
    ) {
        try {
            this.eventsController.assist(eventId, request);

            return Response.ok().build();
        } catch (IllegalArgumentException | NullPointerException ex){
            LOG.log(Level.FINE, "Invalid arguments (eventId, Login)", ex);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Error creating an assist", e);
            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        } catch (NotLoggedInException nle){
            LOG.log(Level.WARNING, "NOT LOGGED IN!", nle);
            return Response.serverError()
                    .entity(nle.getMessage())
                    .build();
        }


    }

    @GET
    @Path("/{id}")
    public Response get(
            @PathParam("id") String id
    ) {
        try {
            final Event event = this.eventsController.get(id);

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
    @Path("/{id}/image")
    @Produces(MediaType.WILDCARD)
    public Response getEventImage(
            @PathParam("id") String id
    ){
        try{
            Image i = this.eventsController.getImage(id);
            return Response.status(200)
                    .type("image/" + i.getImg_ext())
                    .entity(new ByteArrayInputStream(i.getImg()))
                    .build();
            //return Response.status(200).type("image/" + i.getImg_ext()).entity(i.getImg()).build();

        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Error getting an image", e);
            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }


    @GET
    public Response list(
            @QueryParam("type") String type,
            @QueryParam("search") String search,
            @Context HttpServletRequest request
    ) {
        try {

            if(type != null){
                if(type.equals("featured"))
                    return Response.ok(this.eventsController.getFeatured()).build();
                else if(type.equals("popular"))
                    return Response.ok(this.eventsController.getPopular()).build();
                else if(type.equals("assist"))
                    return Response.ok(this.eventsController.getAssistEvents(request)).build();
                else
                    return Response.status(400).build();
            } else if(search != null)
                return Response.ok(this.eventsController.getSearch(search)).build();
            else {
                return Response.ok(this.eventsController.list()).build();
            }
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Error listing events", e);
            return Response.serverError().entity(e.getMessage()).build();
        } catch (NotLoggedInException e) {
            LOG.log(Level.SEVERE, "You must be logged in to access your assistance to future events", e);
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response add(
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("place") String place,
            @FormParam("num_assistants") int    num_assistants,
            @FormParam("start") long start,
            @FormParam("end") long end,
            @FormParam("category") String category,
            @FormParam("img") File img,
            @Context HttpServletRequest request

    ) {
        try {
            Event event = this.eventsController.add(
                    title,
                    description,
                    place,
                    num_assistants,
                    new Date(start),
                    new Date(end),
                    Categories.valueOf(category),
                    null,
                    null,
                    request
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
        } catch (NotLoggedInException e){
            LOG.log(Level.SEVERE, "You are not logged in to add an event", e);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response modify(
            @PathParam("id") String id,
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("place") String place,
            @FormParam("num_assistants") int num_assistants,
            @FormParam("start") Date start,
            @FormParam("end") Date end,
            @FormParam("user_id") String user_id,
            @FormParam("category") String category
    ) {
        try {
            Event event = this.eventsController.modify(
                    id,
                    title,
                    description,
                    place,
                    num_assistants,
                    start,
                    end,
                    user_id,
                    Categories.valueOf(category)
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
            @PathParam("id") String id
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