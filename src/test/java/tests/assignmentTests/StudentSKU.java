package tests.assignmentTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.TestBase;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/*
 URL is not working!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class StudentSKU {
    String baseUri = ConfigurationReader.getProperty("baseUri2");

    @Test (description = "POST action")
    void testPOST(){
        Map<String, Object> requestBody= new HashMap<>();
        requestBody.put("sku", "berliner");
        requestBody.put("description", "Jelly donut");
        requestBody.put("price", "2.99");

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestBody)
                .when()
                .post(baseUri);
//                .post("https://lryu4whyek.excute-api.us-west-2.amazonaws.com/dev/skus");

        response.prettyPrint();

    }


}
