package integration;

import br.com.fd.application.Application;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"integration/stepdefs"},
        features = {"classpath:features"})
public class TestRunner {

    private static Application application;

    @BeforeClass
    public static void setUp() {
        application = new Application();
        application.start();
        application.awaitInitialization();
    }

    @AfterClass
    public static void tearDown() {
        application.stop();
    }

}