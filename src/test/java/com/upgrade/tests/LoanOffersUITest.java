package com.upgrade.tests;

import com.github.javafaker.Faker;
import com.upgrade.pages.*;
import com.upgrade.pojos.Borrower;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Test;
import org.testng.Assert;


import static com.upgrade.tests.BorrowerDetails.getBorrowerNegativeCase;
import static com.upgrade.tests.BorrowerDetails.getBorrowerPositiveCase;

@Log4j
public class LoanOffersUITest extends AbstractTest {
    private static final String URL = "https://www.credify.tech";
    private static final String DECLINE_MESSAGE= "We can't find you a loan offer yet, but you still have great options";

    /*
        Please refer README.md for more details on
        Case # 1 : Validate offers after re-login
    */

    @Test
    public void validateOffersTest()  {
        Borrower borrower = getBorrowerPositiveCase();
        LandingPage landingPage = new LandingPage(getDriver());

        SelectOfferPage firstTrial=null;
        try {
            firstTrial=  landingPage
                    .gotoLandingPage(URL)
                    .enterLoanDetails(borrower)
                    .enterContactDetails(borrower)
                    .enterIncomeDetails(borrower)
                    .enterLoginDetails(borrower);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        String defaultLoanAmt= firstTrial.getLoanAmount();
        String defaultMonthlyPayment = firstTrial.getMonthlyPayment();
        String defaultInterestRate= firstTrial.getInterestRate();
        String defaultTerm = firstTrial.getTerm();
        String defaultApr = firstTrial.getDefaultApr();

       firstTrial.clickSignOut();

        //Validate the offer details after login
        SignInPage signInPage = new SignInPage(getDriver());
        SelectOfferPage secondLogin = signInPage
                .gotoSignInPage(URL)
                .signIn(borrower);

        Assert.assertEquals(secondLogin.getLoanAmount(), defaultLoanAmt);
        Assert.assertEquals(secondLogin.getMonthlyPayment(), defaultMonthlyPayment);
        Assert.assertEquals(secondLogin.getInterestRate(), defaultInterestRate);
        Assert.assertEquals(secondLogin.getTerm(),defaultTerm);
        Assert.assertEquals(secondLogin.getDefaultApr(),defaultApr);

        secondLogin.clickSignOut();
    }

    /*
        Please refer README.md for more details on
        Case # 2  : Loan rejected for low annual income
    */


    @Test
    public void validateDeclineLoanTest() {
        Borrower borrower = getBorrowerNegativeCase();
        LandingPage landingPage = new LandingPage(getDriver());


        DeclinePage declinePage= null;
        try {
            declinePage = landingPage
                    .gotoLandingPage(URL)
                    .enterLoanDetails(borrower)
                    .enterContactDetails(borrower)
                    .enterIncomeDetails(borrower)
                    .enterLoginDetails(borrower);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        String declineMssg= declinePage.getDeclineMessage();
       // System.out.println(declineMssg);
        Assert.assertTrue(declineMssg.equalsIgnoreCase(DECLINE_MESSAGE));

        DocumentsPage documentsPage= declinePage.clickLearnMore();
        String documentsUrl= getDriver().getCurrentUrl();
        Assert.assertTrue(documentsUrl.matches(".*/portal/product/[0-9]+/documents"));

        Assert.assertEquals(documentsPage.isDocLinkExist(), true);

        }
    }



