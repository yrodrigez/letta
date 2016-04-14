package es.uvigo.esei.daa.letta.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.daa.letta.entities.EventUnitTest;
import es.uvigo.esei.daa.letta.entities.ImageUnitTest;
import es.uvigo.esei.daa.letta.entities.UserUnitTest;

@SuiteClasses({
    UserUnitTest.class,
    EventUnitTest.class,
    ImageUnitTest.class
})
@RunWith(Suite.class)
public class UnitTestSuite {
}
