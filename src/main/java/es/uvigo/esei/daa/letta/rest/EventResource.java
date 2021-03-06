package es.uvigo.esei.daa.letta.rest;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.daa.letta.DAO.DAOException;
import es.uvigo.esei.daa.letta.controllers.EventController;
import es.uvigo.esei.daa.letta.controllers.NotLoggedInException;
import es.uvigo.esei.daa.letta.entities.Event;
import es.uvigo.esei.daa.letta.entities.Event.Categories;
import es.uvigo.esei.daa.letta.entities.Image;


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


    @GET
    @Path("/attendance/")
    public Response getAttendance(
            @Context HttpServletRequest request
    ) {
        try {
            final List<Event> events = this.eventsController.getAssistEvents(request);

            return Response.ok(events).build();
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
        } catch (NotLoggedInException nle) {
            LOG.log(Level.WARNING, "NOT LOGGED IN!", nle);
            return Response.serverError()
                    .entity(nle.getMessage())
                    .build();
        }

    }

    @POST
    @Path("/attend/")
    public Response attend(
            @Context HttpServletRequest request,
            @FormParam ("id") int eventId
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
            LOG.log(Level.SEVERE, "Not found that event", e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (NotLoggedInException nle){
            LOG.log(Level.WARNING, "NOT LOGGED IN!", nle);
            return Response.status(Response.Status.UNAUTHORIZED)
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
                else if(type.equals("attend"))
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
    public Response add(
            @FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("place") String place,
            @FormParam("num_assistants") int num_assistants,
            @FormParam("start") String start,
            @FormParam("end") String end,
            @FormParam("category") String category,
            @FormParam("img") String img,
            @FormParam("img_ext") String img_ext,
            @Context HttpServletRequest request

    ) {
        try {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	
            Event event = this.eventsController.add(
                    title,
                    description,
                    place,
                    num_assistants,
                    formatter.parse(start),
                    formatter.parse(end),
                    Categories.valueOf(category),
                    img,
                    img_ext,
                    request
            );

            return Response.ok(event).build();
        } catch (IllegalArgumentException | NullPointerException | ParseException ex ) {
            LOG.log(Level.SEVERE, "Error adding a event", ex);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Error adding a event", e);
            return Response.status(Response.Status.BAD_REQUEST)
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