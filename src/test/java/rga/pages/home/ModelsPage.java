package rga.pages.home;

import common.utils.RandomCharacter;
import common.wdm.WdManager;
import rga.business.dataproviders.ModelsProvider;
import rga.business.entities.ModelEntity;
import rga.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ModelsPage extends BasePage {
    @FindBy(xpath = "//a[contains(@href,'/models/new')]")
    WebElement createBtn;

    @FindBy(xpath = "//button[contains(@class,'ant-btn')]//span[contains(text(),'Import')]")
    WebElement importButton;

    @FindBy(xpath = ".//i[contains(@class,'anticon-arrow-right')]")
    WebElement rightArrow;

    @FindBy(xpath = "//input[@placeholder='Model Name*']")
    WebElement modelNameField;

    @FindBy(xpath = "//input[@placeholder='Default Key']")
    WebElement defaultModelKeyField;

    //Property
    @FindBy(xpath = "//div[contains(text(),'Property Title')]/following-sibling::ul//input")
    List<WebElement> listPropTittleField;


    @FindBy(xpath = "//div[contains(@class,'styled__CommandGroupStyled-sc-189oxhr-3 jUJiNW')]//i[@aria-label='icon: down']")
    List<WebElement> listPropertyDownIcon;

    @FindBy(xpath = "//div[contains(@class,'ant-select-dropdown-placement-bottomLeft')]//ul[@role='listbox']")
    List<WebElement> listDDPropertyType;
    //End Property

    @FindBy(xpath = "//input[@placeholder='Sample']")
    List<WebElement> listSampleField;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement saveBtn;

    @FindBy(xpath = "//div[contains(@class,'ant-modal-footer')]//button[@type='submit']")
    WebElement savePopUpButton;

    @FindBy(xpath = "//textarea[@name='jsonString']")
    WebElement jsonStringField;

    @FindBy(xpath = "//button[contains(@class,'ant-btn')]//span[contains(text(),'Copy')]/..")
    List<WebElement> copyButton;

    @FindBy(xpath = "//i[contains(@class,'anticon anticon-right ant-collapse-arrow')]")
    List<WebElement> collapseArrow;

    @FindBy(xpath = "//button[@title='Describe Data']")
    WebElement describeDataBtn;

    @FindBy(xpath = "//button[contains(@class,'ant-btn')]//span[contains(text(),'Edit')]/..")
    WebElement editButton;

    //Describe Data
    @FindBy(xpath = "//input[@placeholder='Mininum']")
    WebElement minimumField;

    @FindBy(xpath = "//input[@placeholder='Maximum']")
    WebElement maximumField;

    @FindBy(name = "additionalItems")
    WebElement ckAdditionalItems;

    @FindBy(name = "uniqueItems")
    WebElement ckUniqueItems;

    @FindBy(xpath = "//span[contains(.,'Allow Null')]//input")
    WebElement ckAllowNull;

    @FindBy(xpath = "//span[contains(.,'Default is Null')]//input")
    WebElement ckDefaultIsNull;
    //End Describe Data

    @FindBy(xpath = "//button[@title='Add Property']")
    List<WebElement> addPropertyButton;

    @FindBy(xpath = "//button[@title='Type Settings']")
    List<WebElement> typeSettingsButton;

    @FindBy(xpath = "//input[@name='x-pii']")
    WebElement personallyIdentifiableInformationField;

    @FindBy(xpath = "//input[@name='x-unique']")
    WebElement uniqueIdentifierField;

    @FindBy(xpath = "//input[@name='x-allow-null']")
    WebElement allowNullField;

    @FindBy(xpath = "//input[@name='default']")
    WebElement defaultField;

    @FindBy(xpath = "//button[contains(@class,'ant-btn')]//span[contains(text(),'Add Enum')]/..")
    WebElement addEnumButton;

    @FindBy(xpath = "//button[@class='ant-btn ant-btn-danger ant-btn-icon-only']/preceding-sibling::input")
    List<WebElement> addEnumField;

    @FindBy(xpath = "//div[@class='ant-form-explain']")
    WebElement invalidJsonMess;

    @FindBy(xpath = "//input[@placeholder='Search term']")
    WebElement searchTextBox;

    @FindBy(xpath = "//button[contains(@class,'search-button')]")
    WebElement searchButton;

    @FindBy(xpath = "//tr[contains(@class,'ant-table-row')]//td[span[contains(@class,'ant-table-row-indent')]]")
    List<WebElement> modelsResult;

    @FindBy(xpath = "//label[contains(.,'Type')]/../..//i[@aria-label='icon: down']")
    WebElement ddModelDownIcon;

    @FindBy(xpath = "//ul[li[contains(.,'Object')]]")
    WebElement ddModelType;

    public static ModelsPage getInstance() {
        return new ModelsPage();
    }

    private String modelName;

    public void clickCreateButton() {
        click(createBtn);
    }

    public void enterModelName(String modelName) {
        type(modelNameField, modelName);
    }

    public void enterPropertyTittle(String propertyTittle) {
        type(listPropTittleField.get(0), propertyTittle);
    }

    public void enterMultiPropertyTittle(ModelEntity.Properties properties) {
        for (int i = 0; i < properties.propertiesValue.size(); i++) {
            click(addPropertyButton.get(i));
            type(listPropTittleField.get(i),properties.propertiesValue.get(i).split(":")[0]);

            click(listPropertyDownIcon.get(i));
            selectUlByValue(listDDPropertyType.get(i), properties.propertiesValue.get(i).split(":")[1]);
        }

    }

    public void clickSaveButton() {
        click(saveBtn);
    }

    public void importJsonFile(String json, int length) throws InterruptedException {
        click(importButton);
        type(jsonStringField, json);
        Thread.sleep(2000);

        click(savePopUpButton);
        Thread.sleep(2000);
        for (int i = 0; i < length; i++) {
            click(rightArrow);
            Thread.sleep(2000);
        }

        click(savePopUpButton);
        Thread.sleep(3000);
    }

    public int getListPropertyField() {
        return listPropTittleField.size();
    }

    public List<String> getSampleValue() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listSampleField.size(); i++) {
            list.add(listSampleField.get(i).getAttribute("value"));
        }
        return list;
    }

    public void clickCopyButton(int index) {
        scrollToElement(copyButton.get(index -1));
        click(copyButton.get(index - 1));
    }

    public void clickCollapseButton(int index) {
        click(collapseArrow.get(index - 1));
    }

    public List<String> getCellValueOfTable() {
        List<String> list = new ArrayList<>();
        WebElement table = WdManager.get().findElement(By.tagName("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for(WebElement row : rows) {
            list.add(row.getText());
        }
        return list;
    }

    public void clickDescribeDataButton() {
        click(describeDataBtn);
    }

    public void clickEditPopUpButton() {
        click(editButton);
    }

    public void enterDescribeData(ModelEntity.ModelDescribeData modelDescribeData) {
        clickDescribeDataButton();
        clickEditPopUpButton();

        type(minimumField, modelDescribeData.minProperties);
        type(maximumField, modelDescribeData.maxProperties);
        if (modelDescribeData.additionalProperties)
            click(ckAdditionalItems);
//        else if(modelDescribeData.uniqueItem)
//            click(ckUniqueItems);
        else if (modelDescribeData.allowNull)
            click(ckAllowNull);
        else if (modelDescribeData.defaultIsNull)
            click(ckDefaultIsNull);

        click(savePopUpButton);
    }

    public void ClickSavePopupButton() {
        click(savePopUpButton);
    }

    public void ClickTypeSettingButton(int index) {
        click(typeSettingsButton.get(index - 1));
    }

    public void enterTypeSetting(String defaultValue, String enumValue) {
        click(personallyIdentifiableInformationField);
        click(uniqueIdentifierField);
        click(allowNullField);
        type(defaultField, defaultValue);
        String[] arrEnum = enumValue.split(",");
        for (int i = 0; i < arrEnum.length; i++) {
            click(addEnumButton);
        }

        for (int i = 0; i < addEnumField.size(); i++) {
            type(addEnumField.get(i), arrEnum[i]);
        }

        click(savePopUpButton);
    }

    public void importInvalidJsonFile(String json) throws InterruptedException {
        click(importButton);
        type(jsonStringField, json);
        Thread.sleep(2000);

        click(savePopUpButton);
        Thread.sleep(2000);
    }

    public String getInvalidJsonMessage() {
        return getText(invalidJsonMess);
    }

    public int pageListOfModel() {
        return modelsResult.size();
    }

    public String addNewModel(String type){
        ModelEntity modelEntity = ModelsProvider.getModelByType(type);
        modelName = "Automation_Model_" + RandomCharacter.getRandomAlphaNumericString(10);

        clickCreateButton();
        enterModelName(modelName);

        type(defaultModelKeyField, modelEntity.defaultModelKey);

        click(ddModelDownIcon);
        selectUlByValue(ddModelType, modelEntity.modelType);

        enterDescribeData(modelEntity.modelDescribeData);

        enterMultiPropertyTittle(modelEntity.properties);

        click(saveBtn);
        return modelName;
    }
    public String addNewModel(String modelName, String type){
        ModelEntity modelEntity = ModelsProvider.getModelByType(type);

        clickCreateButton();
        enterModelName(modelName);

        type(defaultModelKeyField, modelEntity.defaultModelKey);

        click(ddModelDownIcon);
        selectUlByValue(ddModelType, modelEntity.modelType);

        enterDescribeData(modelEntity.modelDescribeData);

        enterMultiPropertyTittle(modelEntity.properties);

        click(saveBtn);
        return modelName;
    }
}
