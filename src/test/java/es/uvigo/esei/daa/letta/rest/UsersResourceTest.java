package es.uvigo.esei.daa.letta.rest;

import static es.uvigo.esei.daa.letta.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.daa.letta.dataset.UsersDataset.users;
import static es.uvigo.esei.daa.letta.matchers.IsEqualToUser.containsUsersInAnyOrder;
import static org.junit.Assert.assertThat;

import java.io.IOException;
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
import es.uvigo.esei.daa.letta.entities.User;
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
public class UsersResourceTest extends JerseyTest {
	
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
	public void testList() throws IOException {
		final Response response = target("users").request().get();
		assertThat(response, hasOkStatus());
		
		System.out.println(response.getMediaType());
		System.out.println(response);

		final List<User> users = response.readEntity(new GenericType<List<User>>(){});
	
		assertThat(users, containsUsersInAnyOrder(users()));
	}
	

}
