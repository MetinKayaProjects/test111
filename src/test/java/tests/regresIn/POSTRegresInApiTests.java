package tests.regresIn;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert.*;
import org.testng.annotations.Test;
import tests.TestBase;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.*;

public class POSTRegresInApiTests extends TestBase {
/*
POST:
{
    "name": "morpheus",
    "job": "leader"
}

status code: 201
ResponseBody:
{
    "name": "morpheus",
    "job": "leader",
    "id": "939",
    "createdAt": "2022-11-10T05:25:34.713Z"
}
 */

    @Test (description = "CREATE action in https://reqres.in/")
    public void testPOST1(){
        Map<String, Object> mapBody = new HashMap<>();
        mapBody.put("name", "morpheus");
        mapBody.put("job", "leader");

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(mapBody)
                .when()
                .post("/api/users");

        response.prettyPrint();
        logger.info("requestBody posted...");

        assertEquals("name is not correct", response.path("name"), "morpheus");
        assertEquals("job is not matching", response.path("job"), "leader");
        assertTrue(response.path("id"), true);
        assertFalse(response.path("createdAt").toString().isEmpty());
    }

    @Test (description = "same test with HAMCREST method- CREATE action in https://reqres.in/")
    public void testPOST2(){
        String requestBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\",\n" +
                "    \"id\": \"959\",\n" +
                "    \"createdAt\": \"2022-11-10T05:45:17.424Z\"\n" +
                "}";

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/api/users")
                .then().assertThat().statusCode(201)
                .and().assertThat().contentType("application/json;charset=UTF-8")
                .and().assertThat().body("name", equalTo("morpheus"),
                        "job", equalTo("leader"),
                        "id", equalTo("959"),
                        "createdAt", notNullValue()
                );
    }

    @Test (description = "same test with JSONPATH method- CREATE action in https://reqres.in/")
    public void testPOST3(){
        String requestBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\",\n" +
                "    \"id\": \"959\",\n" +
                "    \"createdAt\": \"2022-11-10T05:45:17.424Z\"\n" +
                "}";

        Response response =given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/api/users"); //I store the response in a response object.
        response.prettyPrint();

        JsonPath json = response.jsonPath();//Then I use JSONPATH to navigate in the response body

        assertEquals("name is not correct", "morpheus", json.getString("name"));
        assertEquals("job is not matching", "leader", json.getString("job"));
        assertEquals("id is not correct", "959", json.getString("id"));
        assertTrue("created date is not exist", !json.getString("createdAt").isEmpty());
        assertFalse("created date is not exist", json.getString("createdAt").isEmpty());
    }

}
