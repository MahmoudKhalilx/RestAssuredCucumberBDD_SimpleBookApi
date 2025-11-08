package api.hooks;

import api.utils.TestDataReader;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.restassured.RestAssured;

import java.util.Map;

public class Hooks {
    Map<String, Object> data =
            TestDataReader.readJson("src/test/resources/testData.json");
    @Before
    public void setup() {
        RestAssured.baseURI = data.get("baseURI").toString();
    }

    @After
    public void teardown() {
        // For API not needed but kept for future logs
        System.out.println("Scenario finished!");
    }
}
