package com.aeione.ops.pageobjects;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BoostConsolePageObject {

    @FindBy(xpath = "//*[text()='Boost Console']")
    public WebElement boostconsole_link;

    @FindBy(xpath = "//*[@class='text-md f500 elipse elipse--w-130']")
    public List<WebElement> boostconsole_my_post;

    @FindBy(id = "view-profile-boost-button")
    public WebElement my_post_boost_button;

    @FindBy(xpath = "//*[text()='You must boost between 100 and 5000 views.']")
    public WebElement boost_post_popup_message;

    @FindBy(id = "topbar-dropdown-admin")
    public WebElement admin_link;

    @FindBy(xpath = "//*[text()='Boosts']")
    public WebElement boosts_option;

    @FindBy(xpath = "//*[@class='mdl-card mdl-shadow--4dp boost']")
    public List<WebElement> newsfeed_postcard;

    @FindBy(id = "console-rejected-posts")
    public WebElement rejected_posts_module;

    @FindBy(xpath = "//div[@class='m-modal-confirm-body']//h5[1]")
    public WebElement rejection_popupdisplay;

    //@FindBy(xpath = "//span[contains(.,'Illegal')]")
    //@FindBy(xpath = "//*//div//ul//li[contains(@class,'m-modal-reasons--reasons--reason mdl-list__item')]//span[contains(.,'Illegal')]")
    @FindBy (xpath = "//span[contains(text(),'Appeals on Boost decisions')]")
    public WebElement rejection_reasons;

    @FindBy(id = "rejection_reason_yes_button")
    public WebElement rejection_boost_button;

    @FindBy(xpath = "//*[@class='m-modal-confirm-body']")
    public WebElement rejection_popup;

    @FindBy(id = "rejection_reason_no_button")
    public WebElement rejection_cancel_button;

    @FindBy(xpath = "//*[@class='o-boost-head o-boost-head--border']")
    public WebElement boost_popup_header;

    @FindBy(xpath = "//*[@class='text-sm grey']")
    public List<WebElement> target_option;

    @FindBy(id = "card-views-for")
    public List<WebElement> views_so_far;

    @FindBy(id = "card-boost-time-created")
    public List<WebElement> boosted_post_activity_time;

    @FindBy(id = "card-boost-owner-name")
    public List<WebElement> boosted_post_username;

    @FindBy(xpath = "(//a[@class='o-avatar']//img)[2]")
    public List<WebElement> boosted_post_userimage;


    @FindBy(id = "card-boost-entity-message")
    public List<WebElement> boosted_text;

    @FindBy(xpath = "text-lg grey o-posts-det")
    public WebElement boosted_username;

    @FindBy(id = "card-boost-owner-name")
    public List<WebElement> boostconsole_usernametext;

    @FindBy(xpath = "//*[@class='mdl-card mdl-shadow--8dp']")
    public List<WebElement> boosted_postcard;

    @FindBy(xpath = "//*[contains(@class,'reject-button')]")
    public WebElement boostedpost_admin_reject;


    //*[@class='mdl-list__item-primary-content']
    //span[@class='mdl-list__item-primary-content']
    //(//span[@class='mdl-list__item-primary-content'])[2]
    //li[contains(@class,'m-modal-reasons--reasons--reason mdl-list__item')]//span




}