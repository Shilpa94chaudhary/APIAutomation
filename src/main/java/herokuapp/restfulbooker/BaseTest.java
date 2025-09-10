package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected RequestSpecification spec;

    // Method to set BaseURL
    @BeforeMethod
    public void setUp(){
        spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();
    }

    protected Response createBooking(){
        /*
         *  "firstname" : "Jim", "lastname" : "Brown",
            "totalprice" : 111, "depositpaid" : true,
            "bookingdates" : {
                "checkin" : "2018-01-01",
                "checkout" : "2019-01-01"
            },
            "additionalneeds" : "Breakfast"
         */
        // Create JSON request body
        JSONObject body = new JSONObject();
        JSONObject bookingDates = new JSONObject();
        body.put("firstname","Robert");
        body.put("lastname" , "Downey");
        body.put("totalprice",1000);
        body.put("depositpaid",true);
        bookingDates.put("checkin","2025-09-05");
        bookingDates.put("checkout","2025-09-08");
        body.put("bookingdates",bookingDates);
        body.put("additionalneeds","Breakfast");

        // If you want to print request body uncomment this line
//        System.out.println("Request body: " + body);

        // Get response
        return RestAssured.given().contentType(ContentType.JSON).
                body(body.toString()).post("https://restful-booker.herokuapp.com/booking");
    }
}
