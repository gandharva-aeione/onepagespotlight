package com.aeione.ops.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BlueStorePageObjects
{
    @FindBy( id = "landing_page_Create_an_item")
    public WebElement crete_bluestore_button;

    @FindBy(xpath = "//*[@class='o-create-blue-store']")
    public WebElement bluestore_form;

    @FindBy (id = "landing_page_My_Sales_Board")
    public WebElement landing_page_My_Sales_Board;

    @FindBy (id = "store_list_bluestore_title")
    public List<WebElement> bluestore_card_title;

    @FindBy (xpath = "//*[@id='landing_page_Categories_Widget']//a")
    public  List<WebElement> blueStore_Categories;
}
