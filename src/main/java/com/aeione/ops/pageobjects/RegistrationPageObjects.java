package com.aeione.ops.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RegistrationPageObjects {

    @FindBy(id = "registration-fullname")
    public WebElement registrationFullNameTextField;

    @FindBy(id = "registration-username")
    public WebElement registrationUsernameTextField;

    @FindBy(id = "registration-email")
    public WebElement registrationEmailTextField;

    @FindBy(id = "phone")
    public WebElement phoneTextField;

    @FindBy(xpath = "//*[@class='selected-flag dropdown-toggle']")
    public WebElement countryDropdown;

    @FindBy(id = "registration-date-dropdown")
    public WebElement registrationDateDropdown;

    @FindBy(id = "registration-month-dropdown")
    public WebElement registrationMonthDropdown;

    @FindBy(id = "registration-year-dropdown")
    public WebElement registrationYearDropdown;

    @FindBy(id = "registration-password")
    public WebElement registrationPasswordTextField;

    @FindBy(id = "registration-confirm-password")
    public WebElement registrationConfirmPasswordTextField;

    @FindBy(id = "registration-checkbox")
    public WebElement registrationCheckbox;

    @FindBy(id = "registration-submit")
    public WebElement registrationSubmitButton;

    @FindBy(xpath = "//*[@class='country']")
    public List<WebElement> countryOption;

    @FindBy(xpath = "//*[@class='flag-box']")
    public List<WebElement> countryFlagBoxOption;

    @FindBy(xpath = "//*[@class='country-name']")
    public List<WebElement> countryNameOption;

    @FindBy(xpath = "//*[@class='dial-code']")
    public List<WebElement> dialCodeOption;

    @FindBy(xpath = "//*[contains(text(),'Confirmation failed')]")
    public WebElement confirmationFailedErrorMsg;

    @FindBy(xpath = "//*[contains(@id,'otp_')]")
    public List<WebElement> otpTextFields;

    @FindBy(xpath = "//*[@class='selected-flag dropdown-toggle']/*[contains(@class,'iti-flag')]")
    public WebElement selectedCountry;


}
