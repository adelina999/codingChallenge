package com.upgrade.tests;

import com.upgrade.pojos.lead.BorrowerResumptionInfo;
import com.upgrade.pojos.lead.LeadSecretRequest;
import com.upgrade.pojos.lead.LeadSecretResponse;
import com.upgrade.pojos.lead.LoanAppResumptionInfo;
import io.restassured.http.ContentType;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;


@Log4j
public class LeadSecretApiTest extends AbstractTest {

    private UUID LOAN_APP_UUID = UUID.fromString("b8096ec7-2150-405f-84f5-ae99864b3e96");
    private UUID WRONG_UUID = UUID.fromString("b8096ec7-2150-325f-84f5-ae99864b3e96");
    private String url = "https://credapi.credify.tech/api/brfunnelorch/";

    /*
        Please refer README.md for more details on APT Test
    */
    @Test
    public void leadSecretTest() {
        LeadSecretRequest leadSecretRequest = LeadSecretRequest.builder()
                .loanAppUuid(LOAN_APP_UUID)
                .skipSideEffects(true)
                .build();

        LeadSecretResponse leadSecretResponse= apiRequest()
                .addHeader("x-cf-corr-id", UUID.randomUUID().toString())
                .addHeader("x-cf-source-id", "coding-challenge")
                .setContentType(ContentType.JSON)
                .setRequestUrl(String.format("%s%s", url, "v2/resume/byLeadSecret"))
                .post(leadSecretRequest, 200)
                .getResponse()
                .as(LeadSecretResponse.class);

        LoanAppResumptionInfo loanAppResumptionInfo= leadSecretResponse.getLoanAppResumptionInfo();
        BorrowerResumptionInfo borrowerResumptionInfo= loanAppResumptionInfo.getBorrowerResumptionInfo();



        String  productType= leadSecretResponse.getLoanAppResumptionInfo().getProductType();
        String firstName= borrowerResumptionInfo.getFirstName();
        Boolean ssnRequired= borrowerResumptionInfo.isSsnRequired();
        Assert.assertEquals(productType,"PERSONAL_LOAN" );
        Assert.assertEquals(firstName, "Benjamin");
        Assert.assertEquals((leadSecretResponse.getLoanAppResumptionInfo().getStatus()), "NEW");
        Assert.assertFalse(ssnRequired);

    }

    @Test
    public void wrongUiidTest() {
        LeadSecretRequest leadSecretRequest = LeadSecretRequest.builder()
                .loanAppUuid(WRONG_UUID)
                .skipSideEffects(true)
                .build();

        LeadSecretResponse leadSecretResponse= apiRequest()
                .addHeader("x-cf-corr-id", UUID.randomUUID().toString())
                .addHeader("x-cf-source-id", "coding-challenge")
                .setContentType(ContentType.JSON)
                .setRequestUrl(String.format("%s%s", url, "v2/resume/byLeadSecret"))
                .post(leadSecretRequest, 404)
                .getResponse()
                .as(LeadSecretResponse.class);



    }

}
