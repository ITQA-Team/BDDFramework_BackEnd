package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;



public class ApiTest {
    @Before
    public void setup(){System.out.println("Starting test....");}

    @After
    public void teardown(){System.out.println("Test Completed....");}
}
