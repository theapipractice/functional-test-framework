package rga.pages.home;

import rga.pages.BasePage;
import rga.utils.Wait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchPage extends BasePage {
    public static  SearchPage getInstance() {
       return new SearchPage();
    }

    @FindBy(xpath = "//input[@placeholder='Search term']")
    WebElement searchField;

    @FindBy(xpath = "//button[contains(@class,'search-button')]")
    WebElement searchButton;

    @FindBy(xpath = "//span[i[@aria-label='icon: close-circle']]")
    WebElement closeCircleButton;

    @FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[span[contains(@class,'ant-table-row-indent')]]")
    List<WebElement> searchResult;

    public void enterSearchValue(String value) throws InterruptedException {
        Wait.waitUntilElementToBeClickable(searchField);
        Thread.sleep(3000);
        do {
            type(searchField, value);
            Thread.sleep(1000);

            searchField.sendKeys(Keys.TAB);
            Thread.sleep(1000);

            clickByJs(searchButton);
            Thread.sleep(2000);
        }while (pageListNumber() > 1);
    }

    public void clearSearchValue(String value) throws InterruptedException {
        type(searchField, value);
        click(searchButton);

        Thread.sleep(1000);
        click(closeCircleButton);
        Thread.sleep(2000);
    }

    public void clickClearSearchButton() throws InterruptedException {
        click(closeCircleButton);
        Thread.sleep(2000);
    }

    public String getSearchReturn() {
        try {
            return getText(searchResult.get(0));
        } catch (Exception ex) {
            return "";
        }
    }

    public int pageListNumber() {
        return searchResult.size();
    }
}
