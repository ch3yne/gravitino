/*
 * Copyright 2024 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.integration.test.web.ui;

import com.datastrato.gravitino.integration.test.web.ui.Pages.MetalakePage;
import com.datastrato.gravitino.integration.test.web.ui.utils.AbstractWebIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MetalakePageTest extends AbstractWebIT {
  MetalakePage metalakePage = new MetalakePage(driver);

  @Test
  public void homePage() {
    String title = driver.getTitle();
    Assertions.assertEquals("Gravitino", title);
  }

  @Test
  public void testCreateMetalake() {
    metalakePage.createMetalakeAction();

    boolean status = metalakePage.verifyIsCreatedMetalake();

    if (status) {
      LOG.info("create metalake successful");
    } else {
      Assertions.fail("create metalake failed");
    }
  }

  @Test
  public void testViewMetalakeDetails() {
    metalakePage.viewMetalakeAction();

    boolean status = metalakePage.verifyIsShowDetails();

    if (status) {
      LOG.info("view metalake details successful");
    } else {
      Assertions.fail("view metalake details failed");
    }
  }
}
