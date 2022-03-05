package pages;

import pageObjects.BuyArticlesPageObject;

public class BuyArticlesPage extends BasePage {
    BuyArticlesPageObject buyArticlesPageObject = new BuyArticlesPageObject();

    public BuyArticlesPage() {
        super(driver);
    }

    public void selectItem(String laptopName) {
        clickOnSelectedObjet(BuyArticlesPageObject.ITEMS_LIST, laptopName);
    }
    public void verifyTheLaptopIsDisplayed(String laptopName){
        verifyTheItemIsDisplayed(BuyArticlesPageObject.ITEMS_LIST_ON_CART, laptopName);
    }


}
