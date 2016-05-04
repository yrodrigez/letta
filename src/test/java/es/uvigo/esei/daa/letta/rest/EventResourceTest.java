package es.uvigo.esei.daa.letta.rest;

import static es.uvigo.esei.daa.letta.dataset.EventsDataset.events;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.featured;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getExistentEvent;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getExistentId;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getExistentImage;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.getNonExistentId;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.popular;
import static es.uvigo.esei.daa.letta.dataset.EventsDataset.search;
import static es.uvigo.esei.daa.letta.matchers.HasHttpStatus.hasBadRequestStatus;
import static es.uvigo.esei.daa.letta.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.daa.letta.matchers.IsEqualToEvent.containsEventsInAnyOrder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
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
	drop = "classpath:db/hsqldb-drop.sql"
)
@DatabaseSetup("/datasets/dataset.xml")
@ExpectedDatabase("/datasets/dataset.xml")
public class EventResourceTest extends JerseyTest{
	
	protected Application configure(){
		return new LettaApplication();
	}

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
	public void testAddNonExistentEvent() throws ParseException {
		final Response response = target("events/").request().get();
	}
}
