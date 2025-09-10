package advanceRestAssured;

import herokuapp.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetBookingTest extends BaseTest {
    @Test
    public void getBookingTest(){
        // Use of BaseTest method to create new booking
        Response response = createBooking();
        int bookingID = response.jsonPath().getInt("bookingid");

        // Use of RequestSpecification
        Response bookingResponse = RestAssured.given(spec).get("/booking/" + bookingID);
        bookingResponse.prettyPrint();

    }
}
