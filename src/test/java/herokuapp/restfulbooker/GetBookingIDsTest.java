package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIDsTest {
    @Test
    public void getBookingIDsWIthoutFilterTest(){
        // Get response with booking IDs
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
        response.prettyPrint();

        // Verify response 200
        Assert.assertEquals(response.statusCode(),200,"Status code should be 200, but it is not.");

        // Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(),"List of booking IDs is empty but it should not be.");
    }
}
