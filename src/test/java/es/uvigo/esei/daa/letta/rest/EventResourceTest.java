package es.uvigo.esei.daa.letta.rest;

import static es.uvigo.esei.daa.letta.dataset.EventsDataset.assist;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.events;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.featured;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getExistentCategory;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getExistentEvent;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getExistentId;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getExistentImage;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getExistentImgBase64;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getNonExistentId;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.popular;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.search;
import static es.uvigo.esei.daa.letta.matchers.HasHttpStatus.hasBadRequestStatus;
import static es.uvigo.esei.daa.letta.matchers.HasHttpStatus.hasHttpStatus;
import static es.uvigo.esei.daa.letta.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.daa.letta.matchers.IsEqualToEvent.containsEventsInAnyOrder;
import static es.uvigo.esei.daa.letta.matchers.IsEqualToEvent.equalsToEvent;
import static javax.ws.rs.client.Entity.entity;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import es.uvigo.esei.daa.letta.LettaApplication;
import es.uvigo.esei.daa.letta.LoginFilter;
import es.uvigo.esei.daa.letta.entities.Event;
import es.uvigo.esei.daa.letta.entities.Image;
import es.uvigo.esei.daa.letta.listeners.ApplicationContextBinding;
import es.uvigo.esei.daa.letta.listeners.ApplicationContextJndiBindingTestExecutionListener;
import es.uvigo.esei.daa.letta.listeners.DbManagement;
import es.uvigo.esei.daa.letta.listeners.DbManagementTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/mem-context.xml")
@TestExecutionListeners({
	DbUnitTestExecutionListener.class,
	DbManagementTestExecutionListener.class,
	ApplicationContextJndiBindingTestExecutionListener.class
})
@ApplicationContextBinding(
	jndiUrl = "java:/comp/env/jdbc/letta",
	type = DataSource.class
)
@DbManagement(
	create = "classpath:db/hsqldb.sql",
	drop = "classpath:db/hsqldb-drop.sql",
	afterTest = "classpath:db/hsqldb-after-test.sql"
)
@DatabaseSetup("/datasets/dataset.xml")
@ExpectedDatabase("/datasets/dataset.xml")
public class EventResourceTest extends JerseyTest{
	
	@Override
	protected TestContainerFactory getTestContainerFactory() {
	   return new GrizzlyWebTestContainerFactory();
	}
	
	@Override
	protected DeploymentContext configureDeployment() {
	   return ServletDeploymentContext.forServlet(
	      new ServletContainer(ResourceConfig.forApplication(configure()))
	   )
	      .servletPath("/rest")
	      .addFilter(LoginFilter.class, "login-filter")
	   .build();
	}
	protected Application configure(){
		return new LettaApplication();
	}
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected void configureClient(ClientConfig config) {
		super.configureClient(config);
		
		// Enables JSON transformation in client
		config.register(JacksonJsonProvider.class);
		config.property("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
	}
	
	@Test
	public void testList() throws IOException, ParseException {
		final Response response = target("events").request().get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
	
		assertThat(events, containsEventsInAnyOrder(events()));
	}
	
	@Test
	public void testPopular() throws IOException, ParseException {
		final Response response = target("events").queryParam("type", "popular").request().get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
	
		assertThat(events, containsEventsInAnyOrder(popular()));
	}
	
	@Test
	public void testFeatured() throws IOException, ParseException {
		final Response response = target("events").queryParam("type", "featured").request().get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
	
		assertThat(events, containsEventsInAnyOrder(featured()));
	}
	
	@Test
	public void testTypeWrong() throws IOException, ParseException {
		final Response response = target("events").queryParam("type", "asdasd").request().get();
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	public void testGetImage(){
		final Response response = target("events/" + getExistentId() + "/image").request().get();
		assertThat(response, hasOkStatus());
		String ext = response.getMediaType().getSubtype();
		byte[] img = response.readEntity(byte[].class);
		System.out.println(img.length);
		
		final Image image = new Image(Image.ExtensionTypes.valueOf(ext), img);
		Image i2 = getExistentImage();
		
		assertEquals(image.getImg_ext(), i2.getImg_ext());
		assertArrayEquals(image.getImg(), i2.getImg());
	}
	
	@Test
	public void testGetEvent() throws ParseException{
		final Response response = target("events/" + getExistentId()).request().get();
		assertThat(response, hasOkStatus());
		Event e = response.readEntity(Event.class);
		
		assertThat(e, is(equalTo(getExistentEvent())));
	}
	
	@Test
	public void testGetSearchEvent() throws ParseException{
		final Response response = target("events").queryParam("search", "Foo description 2")
				.request().get();
		assertThat(response, hasOkStatus());

		
		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
		
		assertThat(events, containsEventsInAnyOrder(search()));
	}
	

	@Test
	public void testGetSearchNotExistentEvent() throws ParseException{
		final Response response = target("events").queryParam("search", "Foo description 999999")
				.request().get();
		assertThat(response, hasOkStatus());

		
		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
		
		assertThat(events, is(empty()));
	}

	@Test
	public void testGetNonExistentEvent() throws ParseException{
		final Response response = target("events/" + getNonExistentId()).request().get();
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/event/add.xml")
	public void testAddNonExistentEvent() throws ParseException {
		
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));
		formEvent.param("img", getExistentImgBase64());
		formEvent.param("img_ext", "png");
		
		final Response response = target("events")
			.request()
			.cookie("token", getToken("user5","55555555555555555555555555555555"))
		.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasOkStatus());

		final Event event = response.readEntity(Event.class);
		System.out.println(event.getId());

		assertThat(event, is(equalsToEvent(new Event(
				16,
				"Event16",
				"Foo description 16",
				"Ons",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				getExistentCategory(),
				true
		))));
	}
	
	@Test
	@ExpectedDatabase("/datasets/event/add2.xml")
	public void testAddNonExistentEventWithoutImage() throws ParseException {
		
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));
		formEvent.param("img", null);
		formEvent.param("img_ext", null);
		
		final Response response = target("events")
			.request()
			.cookie("token", getToken("user5","55555555555555555555555555555555"))
		.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasOkStatus());

		final Event event = response.readEntity(Event.class);

		assertThat(event, is(equalsToEvent(new Event(
				16,
				"Event16",
				"Foo description 16",
				"Ons",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				getExistentCategory(),
				false
		))));
	}
	
	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNonExistentEventWithImgWithoutImg_ext() throws ParseException {
		
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));
		formEvent.param("img", getExistentImgBase64());
		formEvent.param("img_ext", null);
		
		final Response response = target("events")
			.request()
			.cookie("token", getToken("user5","55555555555555555555555555555555"))
		.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNonExistentEventWithImg_extWithoutImg() throws ParseException {
		
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));
		formEvent.param("img", null);
		formEvent.param("img_ext", "png");
		
		final Response response = target("events")
			.request()
			.cookie("token", getToken("user5","55555555555555555555555555555555"))
		.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasBadRequestStatus());
	}
	
	private String getToken(String login, String password) {
		return Base64.getEncoder().encodeToString((login + ":" + password).getBytes());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNullEvent() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", null);
		formEvent.param("description", null);
		formEvent.param("place", null);
		formEvent.param("num_assistants", null);
		formEvent.param("start", null);
		formEvent.param("end", null);
		formEvent.param("category", null);

		final Response response = target("events")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNullTitle() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", null);
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));

		final Response response = target("events")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNullDescription() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", null);
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));

		final Response response = target("events")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNullPlace() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", null);
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));

		final Response response = target("events")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNullNumAssistants() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", null);
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));

		final Response response = target("events")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNullStart() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", null);
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", String.valueOf(getExistentCategory()));

		final Response response = target("events")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNullEnd() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", null);
		formEvent.param("category", String.valueOf(getExistentCategory()));

		final Response response = target("events")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNotLoggedIn() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("user_id", null);
		formEvent.param("category", String.valueOf(getExistentCategory()));

		final Response response = target("events")
				.request()
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasHttpStatus(401));
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAddNullCategory() throws ParseException {
		final Form formEvent = new Form();
		formEvent.param("title", "Event16");
		formEvent.param("description", "Foo description 16");
		formEvent.param("place", "Ons");
		formEvent.param("num_assistants", "18");
		formEvent.param("start", Long.toString(formatter.parse("2050-10-08 20:00:00").getTime()));
		formEvent.param("end", Long.toString(formatter.parse("2050-10-08 23:00:00").getTime()));
		formEvent.param("category", null);

		final Response response = target("events")
				.request()
				.post(entity(formEvent, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasBadRequestStatus());
	}

	@Test
	public void testListAssistEventsLoggedIn() throws ParseException {
		final Response response = target("events").queryParam("type", "attend")
				.request()
				.cookie("token", getToken("user5", "55555555555555555555555555555555"))
				.get();

		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});

		assertThat(events, containsEventsInAnyOrder(assist()));
	}

	@Test
	public void testListAssistEventsNotLoggedIn() throws ParseException {
		final Response response = target("events").queryParam("type", "attend")
				.request()
				.get();

		assertThat(response, hasHttpStatus(401));
	}

	@Test
	@ExpectedDatabase(value = "/datasets/event/attend.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testEventAttend() throws ParseException {

		final Form form= new Form();
		form.param("id", Integer.toString(getExistentId()));

		final Response response = target("events/attend")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasOkStatus());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAttendNotLogedInAndUnauthorizedStatus() throws ParseException {

		final Form form= new Form();
		form.param("id", Integer.toString(getExistentId()));

		final Response response = target("events/attend")
				.request()
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasHttpStatus(401));
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAttendAndNotFoundStatus() throws ParseException {

		final Form form= new Form();
		form.param("id", Integer.toString(getNonExistentId()));

		final Response response = target("events/attend")
				.request()
				.cookie("token", getToken("user5","55555555555555555555555555555555"))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasHttpStatus(404));
	}

	@Test
	@ExpectedDatabase("/datasets/dataset.xml")
	public void testAttendNotAValidUserAndUnathorizedStatus() throws ParseException {

		final Form form= new Form();
		form.param("id", Integer.toString(getNonExistentId()));

		final Response response = target("events/attend")
				.request()
				.cookie("token", getToken("NotAvalidUserId","NotAValidPasswurd"))
				.post(entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertThat(response, hasHttpStatus(401));
	}


}
