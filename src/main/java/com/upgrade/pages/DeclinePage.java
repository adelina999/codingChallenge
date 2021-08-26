package com.upgrade.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Log4j
public class DeclinePage extends FunnelBasePage{

    //@FindBy(className="sc-gDyJDg cArYWi title--secondary text--weight-light text--color-primary")
    @FindBy(css = "main > div > div > div > h2")
    private WebElement declineMessage;

    @FindBy(css = "[data-auto='adverse-learn-more-link']")
    private WebElement infoLink;

    public DeclinePage(WebDriver driver) {
        super(driver);
    }

    public DocumentsPage clickLearnMore(){
        click(infoLink);
        waitForPage();
        return new DocumentsPage(driver);
    }

    public String getDeclineMessage(){
        waitForPage();
        return declineMessage.getText();
    }


}
