package rga.pages.home;

import common.utils.RandomCharacter;
import rga.business.dataproviders.PropertiesProvider;
import rga.business.entities.PropertyEntity;
import rga.pages.BasePage;
import rga.pages.common.NotificationMessagePage;
import rga.utils.Wait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import java.util.List;

public class PropertiesPage extends BasePage {
    private static PropertiesPage instance;
    public static  PropertiesPage getInstance(){
        return new PropertiesPage();
    }

    private String propertyValue;
    @FindBy(how = How.XPATH, using = "//button[span[.='Create']]")
    WebElement createButton;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Property Name*']")
    WebElement propertyName;

	@FindBy(how = How.XPATH, using = "//button[span[.='Add']]")
    WebElement enumButton;

	@FindBy(how = How.XPATH, using = "//span[@class='ant-input-group-wrapper']//input")
    List<WebElement> enumTextField;

	@FindBy(how = How.XPATH, using = "//button[span[.='Save']]")
    WebElement saveButton;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Mininum']")
    WebElement miniNumTextField;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Maximum']")
    WebElement maximumTextField;

	@FindBy(how = How.XPATH, using = "//div[@class='ant-form-explain']")
    WebElement propertyNamError;

	@FindBy(how = How.XPATH, using = "//table[@class='ant-table-fixed']//a[.='Edit']")
    List<WebElement> editLink;

    @FindBy(xpath = "//i[@aria-label='icon: edit']")
    WebElement editPropertyButton;

    @FindBy(xpath = "//*[@id='root']/div/section/main/div/div/form/div[3]/div[2]/div/div/span/div/div/div[2]/div/div")
    WebElement typeDownButton;

    @FindBy(xpath = "//span[@class='ant-form-item-children']//i[@aria-label='icon: check']")
    WebElement checkPropertyButton;

    @FindBy(xpath = "//input[contains(@class,'ant-input ModelTitleHeader')]")
    WebElement editPropertyTextField;

    @FindBy(xpath = "//span[contains(@class,'SampleField__SampleInputStyled')]")
    WebElement spSample;

    @FindBy(xpath = "//ul[@role='listbox']")
    WebElement ddType;

    @FindBy(xpath = "//label[contains(.,'Faker')]/../..//i[@aria-label='icon: down']")
    WebElement fakerDownButton;

    @FindBy(xpath = "//label[contains(.,'Format')]/../..//i[@aria-label='icon: down']")
    WebElement formatDownButton;

    @FindBy(xpath = "//input[@placeholder='Regular expression']")
    WebElement patternRegularExpressionField;

    @FindBy(xpath = "//input[@placeholder='Description']")
    WebElement descriptionField;

    @FindBy(xpath = "//input[@placeholder='Default']")
    WebElement defaultField;

    @FindBy(xpath = "//div[contains(@style,'min-width:') and contains(@class,'ant-select-dropdown-placement-bottomLeft')]//ul[@role='listbox']")
    WebElement ddFaker;

    @FindBy(xpath = "//div[not(contains(@style,'min-width:')) and contains(@class,'ant-select-dropdown-placement-bottomLeft')]//ul[@role='listbox']")
    WebElement ddFormat;

    @FindBy(xpath = "//label[contains(.,'Version')]")
    WebElement lblVersion;

    @FindBy(xpath = "//input[@class='ant-select-search__field']")
    WebElement searchDropDownFiled;

    @FindBy(xpath = "//h1[contains(@class,'ModelTitleHeader')]")
    WebElement propertyNameLabelError;

    @FindBy(xpath = "//span[contains(@placeholder,'PropertyName')]")
    WebElement propertyNameSpan;

    public String addNewProperty() {
        propertyValue = "Automation_" + RandomCharacter.getRandomAlphaNumericString(9);
        String[] enumValue = new String[]{ "VN", "US", "AU" };

        click(createButton);
        Wait.waitUntilElementDisplay(editPropertyButton, 60);

        click(editPropertyButton);
        type(editPropertyTextField, propertyValue);
        //add Enum
        for (int i = 0; i < 3; i++)
        {
            click(enumButton);
            type(enumTextField.get(i),enumValue[i]);
        }

        type(miniNumTextField, "1");
        type(maximumTextField, "10");

        clickByJs(checkPropertyButton);
        click(saveButton);
        return propertyValue;
    }

    public void addNewProperty(String name) {
        propertyValue = name;
        String[] enumValue = new String[] { "VN", "US", "AU" };

        click(createButton);
        Wait.waitUntilElementDisplay(editPropertyButton, 60);

        click(editPropertyButton);
        type(editPropertyTextField, propertyValue);

        //add Enum
        for (int i = 0; i < 3; i++) {
            click(enumButton);
            type(enumTextField.get(i), enumValue[i]);
        }

        type(miniNumTextField, "1");
        type(maximumTextField, "10");

        clickByJs(checkPropertyButton);
        click(saveButton);
    }

    public String getPropertyNameError() {
        return getText(propertyNamError);
    }

    public String editProperty(String type) throws InterruptedException {
        propertyValue = "Automation_" + RandomCharacter.getRandomAlphaNumericString(9);
        PropertyEntity propertyEntity = null;

        click(editLink.get(0));
        Wait.waitUntilElementDisplay(editPropertyButton, 60);

        click(editPropertyButton);
        //type(editPropertyTextField, propertyValue);

        switch (type){
            case "String" :
                propertyEntity = PropertiesProvider.getPropertyByType("StringType");

                clickByJs(typeDownButton);
                selectUlByValue(ddType, propertyEntity.stringType.propertyType);
                Thread.sleep(500);

                reTypeAndTab(spSample, propertyEntity.stringType.sample);
                Thread.sleep(500);

                clickByJs(formatDownButton);
                selectUlByValue(ddFormat, propertyEntity.describeStringData.format);
                Thread.sleep(500);

                typeValueByJs(miniNumTextField, String.valueOf(propertyEntity.describeStringData.stringMinimumLength));
                typeValueByJs(maximumTextField, String.valueOf(propertyEntity.describeStringData.stringMaximumLength));

                //type(patternRegularExpressionField, propertyEntity.describeStringData.patternRegularExpression);
                //type(descriptionField, propertyEntity.describeStringData.description);
                break;
            case "Integer":
                propertyEntity = PropertiesProvider.getPropertyByType("IntegerType");

                click(typeDownButton);
                selectUlByValue(ddType, propertyEntity.integerType.propertyType);
                Thread.sleep(500);

                reTypeAndTab(spSample, String.valueOf(propertyEntity.integerType.sample));
                Thread.sleep(500);

                clickByJs(formatDownButton);
                selectUlByValue(ddFormat, propertyEntity.describeIntegerData.format);
                Thread.sleep(500);

                typeValueByJs(miniNumTextField, String.valueOf(propertyEntity.describeIntegerData.minimumValue));
                typeValueByJs(maximumTextField, String.valueOf(propertyEntity.describeIntegerData.MaximumValue));
                break;
            case "Number":
                propertyEntity = PropertiesProvider.getPropertyByType("NumberType");

                click(typeDownButton);
                selectUlByValue(ddType, propertyEntity.integerType.propertyType);
                Thread.sleep(500);

                reTypeAndTab(spSample, String.valueOf(propertyEntity.integerType.sample));
                Thread.sleep(500);

                clickByJs(formatDownButton);
                selectUlByValue(ddFormat, propertyEntity.describeIntegerData.format);
                Thread.sleep(500);

                typeValueByJs(miniNumTextField, String.valueOf(propertyEntity.describeIntegerData.minimumValue));
                typeValueByJs(maximumTextField, String.valueOf(propertyEntity.describeIntegerData.MaximumValue));
                break;
            case "Boolean":
                propertyEntity = PropertiesProvider.getPropertyByType("BooleanType");
                selectUlByValue(ddType, propertyEntity.stringType.propertyType);
                break;

        }

        type(defaultField, propertyEntity.defaultValue);
        defaultField.sendKeys(Keys.TAB);
        Thread.sleep(1000);

        clickByJs(fakerDownButton);
        typeAndTab(searchDropDownFiled, propertyEntity.faker);
        Thread.sleep(1000);

        clickByJs(checkPropertyButton);
        click(saveButton);

        NotificationMessagePage.getInstance().waitNotificationMessageDisplay(15);
        return propertyValue;
    }

    public String getErrorOfPropertyField(){
        return propertyNameLabelError.getAttribute("error");
    }

    public String getErrorOfPropertySpanField(){
        return propertyNameSpan.getAttribute("type");
    }

}
