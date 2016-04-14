package es.uvigo.esei.daa.letta.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.daa.letta.rest.EventResourceTest;
import es.uvigo.esei.daa.letta.rest.UsersResourceTest;


@SuiteClasses({
	UsersResourceTest.class,
	EventResourceTest.class
})
@RunWith(Suite.class)
public class IntegrationTestSuite {
}
