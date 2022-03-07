package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.ManagePetsPage;
import utils.RestAssuredExtension;

import java.util.List;

public class ManagePetsSteps {
    ManagePetsPage managePetsPage = new ManagePetsPage();

    @Given("^Retrieve data from api services with path (.*) from (.*) and save variables bellow$")
    public void dataFromApiServicesAndSaveVariables(String path, String body, List<List<String>> t_table) {
        managePetsPage.getDataFromApiServices(path, body, t_table);
    }

    @Given("^Find pet by id (.*) in the service whith path (.*)$")
    public void findById(String id, String path) {
        managePetsPage.findByPetId(id, path);
    }

    @Then("^The service responds with status (.*)$")
    public void verifyStatusCode(String statusCode) {
        Assert.assertTrue(managePetsPage.verifyServiceStatusCode(statusCode));
    }

    @Then("Update an existing pet with path (.*) from (.*)$")
    public void updateAnExistingPet(String path, String body) {
        managePetsPage.updateExistingPets(path, body);
    }

}
