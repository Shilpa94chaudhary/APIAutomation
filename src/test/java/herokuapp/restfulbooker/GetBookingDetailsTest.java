package herokuapp.restfulbooker;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetBookingDetailsTest extends BaseTest{
    @Test
    public void getBookingDetailsTest(){
        Response response = createBooking();
        response.prettyPrint();

        // Write assertions to validate booking detail
    }
}
