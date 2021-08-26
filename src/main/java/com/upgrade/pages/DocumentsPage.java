package com.upgrade.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;


import java.util.List;

@Log4j
public class DocumentsPage extends FunnelBasePage{

  @FindBys({
           @FindBy(css = "table> tbody"),
           @FindBy(tagName = "tr")
   })
  private List<WebElement> rows;

    public DocumentsPage(WebDriver driver){
        super(driver);
    }

    public boolean isDocLinkExist(){

        boolean isDoc = false;
        boolean isLink = false;

        for (WebElement row: rows){
            List<WebElement> column= row.findElements(By.tagName("td"));
            if(column.get(0).getText().equalsIgnoreCase("Adverse Action Notice.pdf")){
                isDoc=true;
                if(column.get(2).findElement(By.tagName("a")).getAttribute("data-auto").equalsIgnoreCase("downloadDocument")){
                    isLink=true;
                } else
                {
                    System.out.println("Link doesn't exists");
                }
                break;
            }
        }

        if(!isDoc){
            System.out.println("Adverse Action Notice.pdf document doesn't exists");
        }
            return isLink;
    }

}
