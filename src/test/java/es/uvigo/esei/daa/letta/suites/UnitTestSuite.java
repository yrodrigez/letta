package es.uvigo.esei.daa.letta.suites;

import es.uvigo.esei.daa.letta.entities.UserUnitTest;
import es.uvigo.esei.daa.letta.entities.EventUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * Created by yago on 6/04/16.
 */
@SuiteClasses({
    UserUnitTest.class,
    EventUnitTest.class
})
@RunWith(Suite.class)
public class UnitTestSuite {
}