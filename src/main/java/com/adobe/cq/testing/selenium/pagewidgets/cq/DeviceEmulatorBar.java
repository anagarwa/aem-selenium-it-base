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
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady.waitCoralReady;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class DeviceEmulatorBar extends BaseComponent {

    private static final String CONTENT_FRAME = "#ContentFrame";
    private static final String EMULATORBAR_IDENTIFIER = ".editor-EmulatorBar";
    private static final String BUTTON_SELECTOR = "button.js-editor-EmulatorBar-toggle";
    private static final String EMULATORBAR_TOOLBAR_IDENTIFIER = ".editor-EmulatorBar-toolbar";
    private static final String EMULATORBAR_RULER_IDENTIFIER = ".editor-EmulatorBar-ruler";
    private static final String EMULATORBAR_TOOLBAR_ACTIONS_IDENTIFIER = ".editor-EmulatorBar-toolbar-actions";
    private static final String EMULATORBAR_TOOLBAR_SWITCHER_IDENTIFIER = ".editor-EmulatorBar-switcher--width";
    private static final String EMULATORBAR_TOOLBAR_DEVICE_POPOVER_BUTTON = ".editor-EmulatorBar-toolbar-actions button[icon='deviceDesktop']";
    private static final CoralSelectList EMULATORBAR_TOOLBAR_DEVICE_POPOVER = new CoralSelectList(".js-editor-EmulatorDeviceList");


    private SelenideElement button = $(BUTTON_SELECTOR);

    public DeviceEmulatorBar() { super(EMULATORBAR_IDENTIFIER); }

    /**
     * @return True if the LayoutEmulatorBar is displayed, so open and visible.
     */
    public boolean isOpen() {
        return isVisible();
    }

    /**
     * @return True if the LayoutEmulatorBar is not displayed, so not open and not visible.
     */
    public boolean isClosed() {
        return !isOpen();
    }

    /**
     * Opens the LayoutEmulatorBar if not already opened
     */
    public DeviceEmulatorBar open() {
        if (isClosed()) {
            toggleDeviceEmulatorBar();
            element().should(exist).shouldBe(visible);
        }
        return this;
    }

    /**
     * Close the LayoutEmulatorBar if not already closed
     */
    public DeviceEmulatorBar close() {
        if (isOpen()) {
            toggleDeviceEmulatorBar();
            element().should(exist).shouldNotBe(visible);
        }
        return this;
    }

    public SelenideElement getToolbar() { return $(EMULATORBAR_TOOLBAR_IDENTIFIER); }
    public SelenideElement getRuler() { return $(EMULATORBAR_RULER_IDENTIFIER); }
    public SelenideElement getToolbarActions() { return $(EMULATORBAR_TOOLBAR_ACTIONS_IDENTIFIER); }

    public DeviceEmulatorBar selectDevice(final DeviceType deviceType) {
        String selector = EMULATORBAR_TOOLBAR_SWITCHER_IDENTIFIER + " " + deviceType.getSelector();
        clickableClick($(selector));
        waitForElementAnimationFinished($(CONTENT_FRAME));
        return this;
    }

    /**
     * Click on the button to toggle device emulator bar.
     */
    private void toggleDeviceEmulatorBar() {
        waitCoralReady(BUTTON_SELECTOR);
        button.click();
    }

    public void openDeviceSelectorPopover() {
        toggleDeviceSelectorPopover(false);
    }

    public void closeDeviceSelectorPopover() {
        toggleDeviceSelectorPopover(true);
    }

    private void toggleDeviceSelectorPopover(boolean mode) {
        SelenideElement popoverButton = element().find(EMULATORBAR_TOOLBAR_DEVICE_POPOVER_BUTTON);
        if (mode == popoverButton.has(cssClass("is-selected"))) {
            clickableClick(popoverButton);
        }
    }

    public void selectDevicePopoverItem(final DeviceType deviceType) {
        openDeviceSelectorPopover();
        EMULATORBAR_TOOLBAR_DEVICE_POPOVER.selectByValue(deviceType.getName());
    }

    public enum DeviceType {

        EMULATORBAR_DEVICES_NATIVE("native"),
        EMULATORBAR_DEVICES_IPAD3("ipad3"),
        EMULATORBAR_DEVICES_IPHONE8PLUS("iphone8plus"),
        EMULATORBAR_DEVICES_IPHONEX("iphonex"),
        EMULATORBAR_DEVICES_GALAXY7("galaxy7");

        private final String name;

        DeviceType(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getSelector() {
            return "[data-device='" + name + "']";
        }
    }

}
