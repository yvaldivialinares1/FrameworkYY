package pages;

import pageObjects.ManagePetsPageObject;
import utils.RestAssuredExtension;

import java.util.List;

public class ManagePetsPage extends BasePage {
    ManagePetsPageObject managePetsPageObject = new ManagePetsPageObject();

    public ManagePetsPage() {
        super(driver);
    }

    public void findByPetId(String id, String path) {
        String idContext = getScenarioContextVariables(id);
        System.out.println(idContext);
        RestAssuredExtension.getMethod(path + idContext);
        RestAssuredExtension.response.getBody().prettyPrint();
    }

    public boolean verifyServiceStatusCode(String statusCode) {
        return RestAssuredExtension.response.statusCode() == Integer.parseInt(statusCode);
    }

    public void updateExistingPets(String path, String body) {
        RestAssuredExtension.putMethod(path, body);
        RestAssuredExtension.response.getBody().prettyPrint();
    }

}
