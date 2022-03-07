package pages;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.BuyArticlesPageObject;

import java.util.concurrent.TimeUnit;

public class BuyArticlesPage extends BasePage {
    BuyArticlesPageObject buyArticlesPageObject = new BuyArticlesPageObject();

    public BuyArticlesPage() {
        super(driver);
    }

    public void selectItem(String laptopName) {
        clickOnSelectedObjet(buyArticlesPageObject.ITEMS_LIST, laptopName);
    }

    public void verifyTheLaptopIsDisplayed(String laptopName) {
        verifyTheItemIsDisplayed(buyArticlesPageObject.ITEMS_LIST_ON_CART, laptopName);
    }
}
