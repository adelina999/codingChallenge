package com.upgrade.pages;


import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;


@Log4j
public class SelectOfferPage extends FunnelBasePage {


    @FindBy(css = "[data-auto='getDefaultLoan']")
    private WebElement continueBtn;

    @FindBy(css="[data-auto='userLoanAmount']")
    private WebElement loanAmount;

    @FindBy(css = "div> div> ul> li > div[data-auto='defaultMonthlyPayment']")
    private WebElement monthlyPayment;

    @FindBy(css = "[data-auto='defaultLoanTerm']")
    private WebElement term;

    @FindBy(css = "[data-auto='defaultLoanInterestRate']")
    private WebElement interestRate;

    @FindBy(css = "[data-auto='defaultAPR']")
    private WebElement defaultApr;


    public SelectOfferPage(WebDriver driver) {
        super(driver);
        waitForWebElements(Arrays.asList(continueBtn));
    }

    //Capturing original offered loan details
    public String getLoanAmount() {
        return loanAmount.getText();
    }

    public String getMonthlyPayment() {
        return monthlyPayment.getText();
    }

    public String getTerm() {
        return term.getText();
    }

    public String getInterestRate() {
        return interestRate.getText();
    }

    public String getDefaultApr() {
        return defaultApr.getText();
    }
}
