package tests.regresIn;

//import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.testng.AssertJUnit.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
//import org.testng.Assert.*;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import tests.TestBase;

import java.util.logging.Logger;

public class GETRegresInApiTests extends TestBase {

    private static final Logger logger = Logger.getLogger(GETRegresInApiTests.class.getName());

    //baseUrl: https://reqres.in/

    @Test (description = "LIST USERS")
    void test1_listOfUsers() {
//        Response response = RestAssured.get("https://reqres.in/api/users?page=2");  //if you import static RestAssured we don't need this.
                                                                                      //import static io.restassured.RestAssured.*;
        Response response = get("https://reqres.in/api/users?page=2");
        int statusCode = response.getStatusCode();
//        System.out.println("statusCode = " + statusCode);

        logger.info("statusCode: " + response.getStatusCode());

        assertEquals(statusCode, 200);
//        logger.info("response" + response.getBody().prettyPrint());

        System.out.println("response*** = " + response.getBody().prettyPrint());

//        logger.info("time******************: " + response.getTime());

//        logger.info("header***************:" + response.getHeaders().get("Age").toString());
//        logger.info("header***************:" + response.getHeaders().toString());


        JsonPath jsonPath = response.jsonPath();

        System.out.println("total_pages = " + jsonPath.get("total_pages"));
        int totalPages = jsonPath.get("total_pages");
        assertEquals(totalPages, 2);
        System.out.println("data.id list = " + jsonPath.get("data.id"));

        System.out.println("data.id[0] = " + jsonPath.get("data.id[0]"));
        int dataId0 = jsonPath.get("data.id[0]");
        assertEquals(dataId0, 7);
        System.out.println("data.email[0] = " + jsonPath.get("data.email[0]"));
        assertEquals(jsonPath.get("data.email[0]"), "michael.lawson@reqres.in");
    }

    @Test (description = "SINGLE USER")
    public void test2_SingleUser() {

        Response response = given().accept(ContentType.JSON)
                .get("https://reqres.in/api/users/2");

        int statusCode = response.statusCode();
        response.prettyPrint();
//        System.out.println("responseBody. = " + response.asString());

        logger.info("statusCode = " + statusCode);
//        System.out.println("responseBody = " + responseBody);

        int id = response.body().path("data.id");
        logger.info("id is " + id);

        String first_name = response.getBody().path("data.first_name");
        logger.info("first_name = " + first_name);

        String text = response.body().path("support.text");
        logger.info("text = " + text);

        assertEquals(200, statusCode);
        assertEquals("is isn't matching", id, 2);
        assertEquals("first_name isn't matching", first_name, "Janet");
        assertTrue("text does not contain expected text", text.contains("To keep ReqRes free"));

    }

    @Test (description = "SINGLE USER-2")
    public void test3() {

        RequestSpecification reqspec = given().accept(ContentType.JSON);
        Response response = reqspec
                .get("/api/users/2");
        response.prettyPeek();

        ////////////////

        Response response2 = given().accept(ContentType.JSON)
                .when()
                .get("/api/users/2");  //URL is called from configuration.properties file by TestBase class!

        response2.prettyPeek();

//        JsonPath jsonPath = response.jsonPath();
//        String url = jsonPath.get("support.url");

        assertTrue(response2.contentType().equalsIgnoreCase("application/json; charset=utf-8"));

        logger.info(response2.getBody().path("support.url").toString());
        assertEquals("url is not correct", response2.getBody().path("support.url"), "https://reqres.in/#support-heading");

        assertTrue("text does not contain \"To keep ReqRes free\" text ",
                response2.getBody().path("support.text").toString().contains("To keep ReqRes free"));

    }

    @Test (description = "SINGLE USER NOT FOUND")
    void test4(){

        Response response = given().accept(ContentType.JSON)
                .when()
                .get("/api/users/23");
        response.prettyPeek();

//        JsonPath jsonPath = response.jsonPath();

        int expectedStatusCode= 404;
        int actualStatusCode= response.getStatusCode();
//        System.out.println("actualStatusCode = " + actualStatusCode);
        logger.info("statusCode verification: expectedStatusCode, " + expectedStatusCode + " is compared with actualStatusCode, " + actualStatusCode);

        assertEquals("status code is not \"successful\"", expectedStatusCode, actualStatusCode);

        String expectedContentType= "application/json; charset=utf-8";
        String actualContentType= response.getHeader("Content-Type");
//        System.out.println("actualContentType = " + actualContentType);
        assertEquals("contentType is not matching", expectedContentType, actualContentType);
    }



}
