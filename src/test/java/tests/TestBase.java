package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import tests.regresIn.GETRegresInApiTests;
import utilities.ConfigurationReader;

import java.util.logging.Logger;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.reset;

public class TestBase {
    public static final Logger logger = Logger.getLogger(GETRegresInApiTests.class.getName());


    @BeforeMethod
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("baseUri");
    }

    @AfterMethod
    public static void teardown() {
        reset();
    }
}
