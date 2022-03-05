package apiTest;


import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.RestAssuredExtension;


public class test_1 {
    public static RestAssuredExtension rest = new RestAssuredExtension();
    public static ResponseOptions<Response> response;
    public static String outputScript = new String();
    public static JSONObject bashResponse = new JSONObject();
    public static String petId;

    @BeforeClass
    public static void beforeClass() throws Exception {
        response = RestAssuredExtension.postMethod("v2/pet",
                "addPet.txt");
        petId = response.getBody().jsonPath().get("id").toString();
    }

    @Test
    public void testPostAddPet() {
        response = RestAssuredExtension.postMethod("v2/pet",
                "addPet.txt");
        response.getBody().prettyPrint();

        String status = response.getBody().jsonPath().get("status").toString();
        Assert.assertTrue("The pet could not be added", status.contains("available"));

        String namePet = response.getBody().jsonPath().get("name").toString();
        Assert.assertTrue("The name of pet it is not same", namePet.contains("doggie"));

        int statusCode = response.getStatusCode();
        Assert.assertTrue("Code status is not as expected", statusCode == 200);
        petId = response.getBody().jsonPath().get("id").toString();
    }

    @Test
    public void testGetFindPetByID() {
        response = RestAssuredExtension.getMethod("v2/pet/" + petId);
        response.getBody().prettyPrint();

        String status = response.getBody().jsonPath().get("status").toString();
        Assert.assertTrue("The pet not was found", status.contains("available") || status.contains("string"));

        int statusCode = response.getStatusCode();
        Assert.assertTrue("Code status is not as expected", statusCode == 200);

    }

    @Test
    public void testPutUpdateAnExistingPet() {
        response = RestAssuredExtension.putMethod("v2/pet", "updateExistingPet.txt");
        response.getBody().prettyPrint();

        String status = response.getBody().jsonPath().get("status").toString();
        Assert.assertTrue("The pet not was found", status.contains("available"));

        int statusCode = response.getStatusCode();
        Assert.assertTrue("Code status is not as expected", statusCode == 200);

        String id = response.getBody().jsonPath().get("id").toString();
        Assert.assertTrue("The id is not correct", id.equals("9223372036854775807"));

        String name = response.getBody().jsonPath().get("name").toString();
        Assert.assertTrue("The id is not correct", name.equals("Puggy"));

    }

}
