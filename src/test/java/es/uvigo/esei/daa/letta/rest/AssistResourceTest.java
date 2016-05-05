package es.uvigo.esei.daa.letta.rest;

import static es.uvigo.esei.daa.letta.dataset.AssistsDataset.*;
import static es.uvigo.esei.daa.letta.matchers.HasHttpStatus.hasBadRequestStatus;
import static es.uvigo.esei.daa.letta.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.daa.letta.matchers.IsEqualToAssist.containsAssistsInAnyOrder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
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
public class AssistResourceTest extends JerseyTest{

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
    public void testAssist() throws IOException, ParseException {
        final Response response = target("Assists").request().get();
        assertThat(response, hasOkStatus());

        final List<Event> attendingEvents = response.readEntity(new GenericType<List<Event>>(){});

        assertThat(attendingEvents, containsAssistsInAnyOrder(assists()()));
    }



    @Test
    public void testAssistList() throws IOException, ParseException {
        final Response response = target("assists").request().get();
        assertThat(response, hasOkStatus());

        final List<Assist> assists = response.readEntity(new GenericType<List<Assist>>(){});

        assertThat(events, containsAssistsInAnyOrder(assists()));
    }
}
