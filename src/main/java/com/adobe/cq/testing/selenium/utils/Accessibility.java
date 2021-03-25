/*
 * Copyright 2021 Adobe Systems Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.adobe.cq.testing.selenium.utils;

import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.deque.axe.AXE;
import com.deque.axe.AXE.Builder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class Accessibility {

    private static final Logger LOG = LoggerFactory.getLogger(Coverage.class);
    private static final String AXE_SCRIPT_PATH = "com/adobe/cq/testing/axe/axe.min.js";
    private static final URL scriptUrl = Accessibility.class.getResource(AXE_SCRIPT_PATH);

    /**
     * Helper method for accessibility testing that returns an AXE.Builder instance.
     * The AXE.Builder instance takes the current navigated page and injects the
     * *axe.min.js* script inside the page so that it ca be used to analyze and
     * create reports.
     *
     * @return AXE.Builder
     */
    public static Builder getAxeBuilder() {
        return new Builder(WebDriverRunner.getWebDriver(), scriptUrl);
    }

    /**
     * Helper method for accessibility testing that returns an AXE.Builder instance.
     * The method uses the current Webdriver as returned by WebDriverRunner.getWebDriver()
     * and navigates to the page stated in the "page" parameter.
     * This method considers that the navigated page uses Coral.
     * <p>
     * Then it injects the *axe.min.js* script, it analyzes the page and asserts if
     * there are any accessibility violations on that page.
     * <p>
     * For more fine tuning of the AXE.Builder object use getAxeBuilder() method.
     *
     * @param page - the BasePage that AXE framework should analyze
     */
    public static void runAxe(BasePage page) {
        testAxe(page, null);
    }

    /**
     * Helper method for accessibility testing that returns an AXE.Builder instance.
     * The method uses the current Webdriver as returned by WebDriverRunner.getWebDriver()
     * and navigates to the page stated in the "page" parameter.
     * This method considers that the navigated page uses Coral.
     * <p>
     * Then it injects the *axe.min.js* script, it analyzes the page and asserts if
     * there are any accessibility violations on that page.
     * <p>
     * For more fine tuning of the AXE.Builder object use getAxeBuilder() method.
     *
     * @param page    - the BasePage that AXE framework should analyze
     * @param element - the SelenideElement that AXE framework should analyze
     */
    public static void runAxe(BasePage page, SelenideElement element) {
        testAxe(page, element);
    }

    private static void testAxe(BasePage page, SelenideElement element) {
        // navigate to Page
        if (!page.isOpen()) {
            page.open();
        }

        // get the current webdriver
        WebDriver webdriver = WebDriverRunner.getWebDriver();

        // create the AXE.Builder instance
        JSONObject responseJSON;
        if (element != null) {
            responseJSON = new Builder(webdriver, scriptUrl).analyze(element);
        } else {
            responseJSON = new Builder(webdriver, scriptUrl).analyze();
        }

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            assertTrue(true, "No violations found inside page: " + page.getPath() + ".");
        } else {
            AXE.writeResults(page.getPath(), responseJSON);
            fail(AXE.report(violations));
        }
    }


}
