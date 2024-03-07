/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */

package com.datastrato.gravitino.integration.test.web.ui.Pages;

import com.datastrato.gravitino.integration.test.web.ui.utils.AbstractWebIT;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetalakePage extends AbstractWebIT {

  @FindBy(
      xpath =
          "//div[contains(@class, 'MuiDataGrid-main')]//div[contains(@class, 'MuiDataGrid-virtualScroller')]//div[@role='rowgroup']")
  public WebElement dataViewer;

  @FindBy(xpath = "//*[@id='createMetalakeBtn']")
  public WebElement createMetalakeBtn;

  @FindBy(xpath = "//*[@id='metalakeNameField']")
  public WebElement metalakeNameField;

  @FindBy(xpath = "//*[@id='metalakeCommentField']")
  public WebElement metalakeCommentField;

  @FindBy(xpath = "//*[@id='query-metalake']")
  public WebElement queryMetalakeInput;

  @FindBy(xpath = "//*[@id='submitHandleMetalake']")
  public WebElement submitHandleMetalakeBtn;

  @FindBy(xpath = "//button[@data-refer='confirm-delete']")
  public WebElement confirmDeleteBtn;

  @FindBy(xpath = "//div[@data-refer='metalake-details-drawer']")
  public WebElement metalakeDetailsDrawer;

  @FindBy(xpath = "//button[@data-refer='close-metalake-details-btn']")
  public WebElement closeMetalakeDetailsBtn;

  @FindBy(xpath = "//button[@data-refer='add-metalake-props']")
  public WebElement addMetalakePropsBtn;

  @FindBy(xpath = "//button[@aria-label='Go to next page']")
  public WebElement nextPageBtn;

  @FindBy(xpath = "//button[@aria-label='Go to previous page']")
  public WebElement prevPageBtn;

  public MetalakePage() {
    PageFactory.initElements(driver, this);
  }

  public void setMetalakeNameField(String nameField) {
    metalakeNameField.sendKeys(Keys.chord(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.DELETE));
    metalakeNameField.clear();
    metalakeNameField.sendKeys(nameField);
  }

  public void setMetalakeCommentField(String commentField) {
    metalakeCommentField.sendKeys(Keys.chord(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.DELETE));
    metalakeCommentField.clear();
    metalakeCommentField.sendKeys(commentField);
  }

  public void setQueryInput(String queryInput) {
    clearQueryInput();
    queryMetalakeInput.sendKeys(queryInput);
  }

  public void clearQueryInput() {
    queryMetalakeInput.sendKeys(Keys.chord(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.DELETE));
    queryMetalakeInput.clear();
  }

  public void clickDeleteMetalakeBtn(String name) {
    String xpath = "//button[@data-refer='delete-metalake-" + name + "']";
    WebElement deleteMetalakeBtn = driver.findElement(By.xpath(xpath));
    deleteMetalakeBtn.click();
  }

  public void clickViewMetalakeBtn(String name) {
    String xpath = "//button[@data-refer='view-metalake-" + name + "']";
    WebElement viewMetalakeBtn = driver.findElement(By.xpath(xpath));
    viewMetalakeBtn.click();
  }

  public void clickEditMetalakeBtn(String name) {
    String xpath = "//button[@data-refer='edit-metalake-" + name + "']";
    WebElement editMetalakeBtn = driver.findElement(By.xpath(xpath));
    editMetalakeBtn.click();
  }

  public void setMetalakeProps(int index, String key, String value) {
    String keyPath = "//div[@data-refer='add-props-key-" + index + "']//input[@name='key']";
    WebElement keyInput = driver.findElement(By.xpath(keyPath));
    keyInput.sendKeys(key);

    String valuePath = "//div[@data-refer='add-props-value-" + index + "']//input[@name='value']";
    WebElement valueInput = driver.findElement(By.xpath(valuePath));
    valueInput.sendKeys(value);
  }

  public boolean verifyCreateMetalake(String name) {
    try {
      String rowPath = "//div[@data-id='" + name + "']";
      WebElement createdMetalakeRow = driver.findElement(By.xpath(rowPath));
      boolean isRow = createdMetalakeRow.isDisplayed();

      String linkPath = "//div[@data-field='name']//a[@href='/ui/metalakes?metalake=" + name + "']";
      WebElement createdMetalakeLink = driver.findElement(By.xpath(linkPath));
      boolean isLink = createdMetalakeLink.isDisplayed();
      boolean isText = Objects.equals(createdMetalakeLink.getText(), name);

      return isRow && isLink && isText;
    } catch (Exception e) {
      return false;
    }
  }

//  public boolean verifyQueryMetalake() {
//    try {
//      Thread.sleep(1000);
//      clearQueryInput();
//      Thread.sleep(1000);
//      LOG.info(this.queryMetalakeInput.getText());
//      Thread.sleep(1000);
////      List<WebElement> dataList = dataViewer.findElements(By.xpath(".//div[@data-field='name']"));
////
////      return dataList.size() == 1 && Objects.equals(dataList.get(0).getText(), "test");
//      return true;
//    } catch (Exception e) {
//      return false;
//    }
//  }

  public boolean verifyEditedMetalake(String name) {
    try {
      String xpath = "//div[@data-field='name']//a[@href='/ui/metalakes?metalake=" + name + "']";
      WebElement editedMetalakeLink = driver.findElement(By.xpath(xpath));

      return Objects.equals(editedMetalakeLink.getText(), name);
    } catch (Exception e) {
      return false;
    }
  }

  public boolean verifyShowMetalakeDetails() {
    try {
      metalakeDetailsDrawer.isDisplayed();
      String drawerVisible = metalakeDetailsDrawer.getCssValue("visibility");

      return Objects.equals(drawerVisible, "visible");
    } catch (Exception e) {
      return false;
    } finally {
      closeMetalakeDetailsBtn.click();
    }
  }

  public boolean verifyEmptyMetalake() {
    String xpath = "//div[contains(@class, 'MuiDataGrid-overlay')]";
    WebElement noMetalakeRows = driver.findElement(By.xpath(xpath));

    return Objects.equals(noMetalakeRows.getText(), "No rows");
  }

//  public boolean verifyIsCreatedManyMetalakes() {
//    try {
//      List<WebElement> dataList = dataViewer.findElements(By.xpath(".//div[@data-field='name']"));
//
//      if (dataList.size() == 9) {
//        clickNextPageBtn();
//        List<WebElement> dataListNext =
//            dataViewer.findElements(By.xpath(".//div[@data-field='name']"));
//
//        if (dataListNext.size() == 1) {
//          clickPrevPageBtn();
//          return true;
//        } else {
//          return false;
//        }
//      } else {
//        return false;
//      }
//    } catch (Exception e) {
//      return false;
//    }
//  }

//  public boolean verifyIsLinkedToCatalogsPage() {
//    try {
//      String xpath = "//a[@data-refer='metalake-name-link']";
//      WebElement nameLink = driver.findElement(By.xpath(xpath));
//
//      Wait<WebDriver> wait =
//          new FluentWait<>(driver)
//              .withTimeout(Duration.ofSeconds(2))
//              .pollingEvery(Duration.ofMillis(300))
//              .ignoring(ElementNotInteractableException.class);
//      wait.until(
//          d -> {
//            nameLink.isDisplayed();
//            return true;
//          });
//
//      String url = driver.getCurrentUrl();
//      boolean isUrl = url.contains("/ui/metalakes?metalake=a_test");
//
//      return nameLink.isDisplayed() && isUrl;
//    } catch (Exception e) {
//      return false;
//    }
//  }

//  public void queryMetalakeAction() {
//    enterQueryInput("tes");
//  }

//  public void createManyMetalakesAction() {
//    int[] arraySize = new int[11];
//    for (int i = 0; i < 11; i++) {
//      createMetalakeAction("test_" + (i + 1), "test", true);
//    }
//  }

//  public void linkToCatalogsPageAction() {
//    createMetalakeAction("a_test", "test", true);
//
//    String xpath = "//div[@data-field='name']//a[@href='/ui/metalakes?metalake=a_test']";
//    WebElement metalakeLink = driver.findElement(By.xpath(xpath));
//
//    Wait<WebDriver> wait =
//        new FluentWait<>(driver)
//            .withTimeout(Duration.ofSeconds(2))
//            .pollingEvery(Duration.ofMillis(300))
//            .ignoring(ElementNotInteractableException.class);
//    wait.until(
//        d -> {
//          metalakeLink.isDisplayed();
//          return true;
//        });
//
//    metalakeLink.click();
//  }
}
