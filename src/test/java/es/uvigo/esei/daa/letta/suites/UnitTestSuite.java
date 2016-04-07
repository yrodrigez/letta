package es.uvigo.esei.daa.letta.suites;

import es.uvigo.esei.daa.letta.entities.EventUnitTest;
import es.uvigo.esei.daa.letta.entities.UserUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({
    UserUnitTest.class,
    EventUnitTest.class
})
@RunWith(Suite.class)
public class UnitTestSuite {
}
