/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.integration.test.web.ui;

import com.datastrato.gravitino.integration.test.web.ui.Pages.MetalakePage;
import com.datastrato.gravitino.integration.test.web.ui.utils.AbstractWebIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MetalakePageTest extends AbstractWebIT {
  MetalakePage metalakePage = new MetalakePage();

  @Test
  @Order(0)
  public void homePage() {
    String title = driver.getTitle();
    Assertions.assertEquals("Gravitino", title);
  }

  @Test
  @Order(1)
  public void testCreateMetalake() {
    String name = "metalake_name";
    metalakePage.createMetalakeBtn.click();
    metalakePage.setMetalakeNameField(name);
    metalakePage.setMetalakeCommentField("metalake comment");
    metalakePage.addMetalakePropsBtn.click();
    metalakePage.setMetalakeProps(0, "key1", "value1");
    metalakePage.addMetalakePropsBtn.click();
    metalakePage.setMetalakeProps(1, "key2", "value2");
    metalakePage.submitHandleMetalakeBtn.click();
    Assertions.assertTrue(metalakePage.verifyCreateMetalake(name));
  }

  @Test
  @Order(2)
  public void testViewMetalakeDetails() {
    metalakePage.clickViewMetalakeBtn("metalake_name");
    Assertions.assertTrue(metalakePage.verifyShowMetalakeDetails());
  }

  @Test
  @Order(3)
  public void testEditMetalake() {
    metalakePage.clickEditMetalakeBtn("metalake_name");
    metalakePage.setMetalakeNameField("metalake_name_edited");
    metalakePage.submitHandleMetalakeBtn.click();
    Assertions.assertTrue(metalakePage.verifyEditedMetalake("metalake_name_edited"));
  }

  @Test
  @Order(4)
  public void testDeleteMetalake() {
    metalakePage.clickDeleteMetalakeBtn("metalake_name_edited");
    metalakePage.confirmDeleteBtn.click();
    Assertions.assertTrue(metalakePage.verifyEmptyMetalake());
  }

//  @Test
//  @Order(6)
//  public void testCreateTwoPagesCountMetalakes() {
//    metalakePage.createManyMetalakesAction();
//
//    boolean status = metalakePage.verifyIsCreatedManyMetalakes();
//
//    if (!status) {
//      Assertions.fail("create many metalakes failed");
//    }
//  }

//  @Test
//  @Order(2)
//  public void testQueryMetalake() {
//    metalakePage.queryMetalakeAction();
//
//    boolean status = metalakePage.verifyQueryMetalake();
//
//    if (!status) {
//      Assertions.fail("query metalake failed");
//    }
//  }

//  @Test
//  @Order(7)
//  public void testLinkToCatalogsPage() {
//    metalakePage.linkToCatalogsPageAction();
//
//    boolean status = metalakePage.verifyIsLinkedToCatalogsPage();
//
//    if (!status) {
//      Assertions.fail("link to catalogs page failed");
//    }
//  }
}
