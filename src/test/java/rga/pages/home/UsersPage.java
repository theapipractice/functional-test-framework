package rga.pages.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import rga.business.dataproviders.UsersProvider;
import rga.business.entities.UsersEntity;
import rga.pages.BasePage;
import rga.pages.TableControlBase;

import java.util.List;

public class UsersPage extends BasePage {
    private static UsersPage instance;
    public static  UsersPage getInstance(){
        return new UsersPage();
    }


    @FindBy(xpath = "//input[@placeholder='Search Users']")
    WebElement searchField;

    @FindBy(xpath = "//table[@class='table-auto w-full']")
    WebElement searchResultTable;
    TableControlBase table = new TableControlBase(searchResultTable);


    @FindBy(xpath = "//button[contains(.,'Add User')]")
    WebElement addUserBtn;

    //add user
    @FindBy(xpath = "//input[@placeholder='First Name']")
    WebElement firstNameField;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement lastNameField;

    @FindBy(xpath = "//input[@placeholder='Email']")
    WebElement emailField;

    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement phoneField;

    @FindBy(xpath = "//div[contains(@class,'modal-add-user')]//select[@class='shadow border rounded w-11/12 py-2 px-3 text-grey-darker']")
    WebElement ddRole;

    @FindBy(xpath = "//div[contains(@class,'modal-add-user')]//button[contains(.,'Cancel')]")
    WebElement cancelPopUprBtn;

    @FindBy(xpath = "//div[contains(@class,'modal-add-user')]//button[contains(.,'Save')]")
    WebElement savePopUprBtn;


    public void enterSearch(String value){
        type(searchField, value);
    }

    public void clickSearchButton(){

    }

    public List<List<String>> verifySearchResult(){
       return table.getAllCellValue();
    }

    public void clickAddUserButton(){
        click(addUserBtn);
    }

    public void enterAddUserForm(){
        UsersEntity usersEntity = UsersProvider.getUsersById("1");

        type(firstNameField, usersEntity.firstName);
        type(lastNameField, usersEntity.lastName);
        type(emailField, usersEntity.email);
        type(phoneField, usersEntity.phone);
        selectByVisibleText(ddRole, usersEntity.role);

        click(savePopUprBtn);
    }

    public void clickSaveButton(){
        click(savePopUprBtn);
    }
}
