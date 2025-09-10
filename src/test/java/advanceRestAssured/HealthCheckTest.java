package advanceRestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class HealthCheckTest {
    @Test
    public void healthCheckTest(){
        // RequestSpecification for baseURL
        RequestSpecification spec = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com").build();

        given().
                spec(spec).
        when().
                get("/ping").
        then().
                assertThat().
                statusCode(201);
    }
}
