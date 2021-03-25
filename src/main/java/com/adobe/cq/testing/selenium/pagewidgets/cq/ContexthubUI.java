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

package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.utils.ElementUtils;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.hasWithPolling;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class ContexthubUI extends BaseComponent {

    private static final Logger LOG = LoggerFactory.getLogger(ContexthubUI.class);

    private static final int CONDITION_CHECK_TIMEOUT = 4000;

    private static final String CONTEXTHUB_FRAME = "contexthub-ui-iframe";
    private static final String CONTEXTHUB_IDENTIFIER = "#" + CONTEXTHUB_FRAME;
    private static final String TOGGLE_BUTTON_IDENTIFIER = ".js-editor-ContextHubTrigger";
    private static final String TOGGLE_BUTTON_SELECTED = "is-selected";
    private static final String POPOVER_IDENTIFIER = ".contexthub-popover";
    private static final String RESET_BUTTON_IDENTIFIER = "button.contexthub-reset";
    private static final String PERSONAGROUP_BUTTON_IDENTIFIER = "button[data-mode='persona']";
    private static final String PROFILE_BUTTON_IDENTIFIER = ".module-granite-profile .contexthub-module-button";
    private static final String PROFILE_DESCRIPTION_IDENTIFIER = ".module-granite-profile .contexthub-module-description";
    private static final String LOCATION_BUTTON_IDENTIFIER = ".module-contexthub-location .contexthub-module-button";
    private static final String LOCATION_DESCRIPTION_IDENTIFIER = ".module-contexthub-location .contexthub-module-description";
    private static final String SEGMENTS_BUTTON_IDENTIFIER = ".module-granite-resolvedsegments .contexthub-module-button";
    private static final String SEGMENTS_DESCRIPTION_IDENTIFIER = ".module-granite-resolvedsegments .contexthub-module-description";
    private static final String DEVICEGROUP_BUTTON_IDENTIFIER = "button[data-mode='device']";
    private static final String DEVICE_BUTTON_IDENTIFIER = ".module-contexthub-device .contexthub-module-button";
    private static final String DEVICE_DESCRIPTION_IDENTIFIER = ".module-contexthub-device .contexthub-module-description";
    private static final String SCREENORIENTATION_BUTTON_IDENTIFIER = ".module-contexthub-screen-orientation .contexthub-module-button";
    private static final String SCREENORIENTATION_DESCRIPTION_IDENTIFIER = ".module-contexthub-screen-orientation .contexthub-module-description";

    private boolean contexthubContext = false;
    private SelenideElement toggleButton = $(TOGGLE_BUTTON_IDENTIFIER);
    private SelenideElement resetButton = $(RESET_BUTTON_IDENTIFIER);
    private SelenideElement groupPersonaButton = $(PERSONAGROUP_BUTTON_IDENTIFIER);
    private SelenideElement profileButton = $(PROFILE_BUTTON_IDENTIFIER);
    private SelenideElement profileDescription = $(PROFILE_DESCRIPTION_IDENTIFIER);
    private SelenideElement locationButton = $(LOCATION_BUTTON_IDENTIFIER);
    private SelenideElement locationDescription = $(LOCATION_DESCRIPTION_IDENTIFIER);
    private SelenideElement segmentsButton = $(SEGMENTS_BUTTON_IDENTIFIER);
    private SelenideElement segmentsDescription = $(SEGMENTS_DESCRIPTION_IDENTIFIER);
    private SelenideElement groupDeviceButton = $(DEVICEGROUP_BUTTON_IDENTIFIER);
    private SelenideElement deviceButton = $(DEVICE_BUTTON_IDENTIFIER);
    private SelenideElement deviceDescription = $(DEVICE_DESCRIPTION_IDENTIFIER);
    private SelenideElement screenorientationButton = $(SCREENORIENTATION_BUTTON_IDENTIFIER);
    private SelenideElement screenorientationDescription = $(SCREENORIENTATION_DESCRIPTION_IDENTIFIER);

    public ContexthubUI() {
        super(CONTEXTHUB_IDENTIFIER);
    }

    /**
     * @return True if the Contexthub UI is displayed, so open and visible and toggle button is selected.
     */
    public boolean isOpen() {
        boolean toggleButtonSelected = hasWithPolling(toggleButton, cssClass("is-selected"));
        boolean contexthubUIVisible = ElementUtils.hasWithPolling(element(), visible, CONDITION_CHECK_TIMEOUT);
        LOG.info("contexthub UI visible: " + contexthubUIVisible + " - toggleButton selected: " + toggleButtonSelected);
        return contexthubUIVisible && toggleButtonSelected;
    }

    /**
     * @return True if the Contexthub UI is not displayed, so not open and not visible
     */
    public boolean isClosed() {
        return !isOpen();
    }

    /**
     * Opens the Contexthub UI if not already opened
     */
    public ContexthubUI open() {
        if (isClosed()) {
            clickOpenContexthubUI();
            toggleButton.should(exist).shouldBe(visible).shouldHave(cssClass(TOGGLE_BUTTON_SELECTED));
            element().should(exist).shouldBe(visible);
        }
        return this;
    }

    /**
     * Close the Contexthub UI if not already closed
     */
    public ContexthubUI close() {
        if (isOpen()) {
            clickCloseContexthubUI();
            toggleButton.should(exist).shouldBe(visible).shouldNotHave(cssClass(TOGGLE_BUTTON_SELECTED));
            element().should(exist).shouldNotBe(visible);
        }
        return this;
    }

    /**
     * Switch to the ContextHub Context (ContextHub iframe)
     */
    public ContexthubUI enterContexthubContext() {
        if (!contexthubContext) {
            switchTo().frame(CONTEXTHUB_FRAME);
            contexthubContext = true;
        }
        return this;
    }

    /**
     * Leave the ContextHub Context (ContextHub iframe) and switch back to top
     */
    public ContexthubUI leaveContexthubContext() {
        if (contexthubContext) {
            switchTo().defaultContent();
            contexthubContext = false;
        }
        return this;
    }

    /**
     * Get the Popover element
     */
    public SelenideElement getPopover() {
        if (!contexthubContext) LOG.warn("Change to ContextHub Context first!");
        return  $(POPOVER_IDENTIFIER);
    }

    public boolean isPopoverDisplayed() { return isElementDisplayed(getPopover()); }
    public boolean isGroupPersonaButtonDisplayed() { return isElementDisplayed(groupPersonaButton); }
    public boolean isGroupDeviceButtonDisplayed() { return isElementDisplayed(groupDeviceButton); }

    private boolean isElementDisplayed(SelenideElement element) {
        boolean isElementDisplayed = false;
        if (!contexthubContext) {
            enterContexthubContext();
            isElementDisplayed = hasWithPolling(element, visible);
            leaveContexthubContext();
        } else {
            isElementDisplayed = hasWithPolling(element, visible);
        }
        return isElementDisplayed;
    }

    public ContexthubUI clickResetButton() { return clickButton(resetButton); }
    public ContexthubUI clickGroupPersonButton() { return clickButton(groupPersonaButton); }
    public ContexthubUI clickProfileButton() { return clickButton(profileButton); }
    public ContexthubUI clickProfileDescription() { return clickButton(profileDescription); }
    public ContexthubUI clickLocationButton() { return clickButton(locationButton); }
    public ContexthubUI clickLocationDescription() { return clickButton(locationDescription); }
    public ContexthubUI clickGroupDeviceButton() { return clickButton(groupDeviceButton); }

    public SelenideElement getProfileDescription() { return getDescription(profileDescription); }
    public SelenideElement getLocationDescription() { return getDescription(locationDescription); }
    public SelenideElement getSegmentDescription() { return getDescription(segmentsDescription); }
    public SelenideElement getDeviceDescription() { return getDescription(deviceDescription); }
    public SelenideElement getScreenorientationDescription() { return getDescription(screenorientationDescription); }

    private SelenideElement getDescription(SelenideElement description) {
        if (!contexthubContext) LOG.warn("Change to ContextHub Context first!");
        description.should(exist);
        return description;
    }

    private void clickOpenContexthubUI() {
        if (contexthubContext) {
            LOG.warn("Leave ContextHub Context first!");
        } else {
            toggleButton.should(exist).shouldBe(visible).shouldNotHave(cssClass(TOGGLE_BUTTON_SELECTED));
            clickableClick(toggleButton);
        }
    }

    private void clickCloseContexthubUI() {
        if (contexthubContext) {
            LOG.warn("Leave ContextHub Context first!");
        } else {
            toggleButton.should(exist).shouldBe(visible).shouldHave(cssClass(TOGGLE_BUTTON_SELECTED));
            clickableClick(toggleButton);
        }
    }

    private ContexthubUI clickButton(SelenideElement button) {
        if (!contexthubContext) {
            enterContexthubContext();
            clickableClick(button);
            leaveContexthubContext();
        } else {
            clickableClick(button);
        }
        return this;
    }

}
