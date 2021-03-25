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

package com.adobe.cq.testing.selenium.pagewidgets.granite;

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Switch;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.codeborne.selenide.Selenide.$;

public class KeyboardShortcuts extends Dialog {

    private final Switch aSwitch;

    /**
     * Constructor for KeyboardShortcutsComponent.
     */
    public KeyboardShortcuts() {
        super("coral-dialog.granite-shortcuts-dialog");
        aSwitch = new Switch(String.format("%s coral-switch", getCssSelector()));
    }

    /**
     * Check if the shortcuts are enabled.
     * This method is a facade for accessing the SwitchComponent directly.
     * Case-study: At any point in time facades can exist in the API to ease the testing
     * as long as the code logic is not duplicated.
     *
     * @return true if enabled.
     */
    public boolean isEnabled() {
        return aSwitch.isEnabled();
    }

    /**
     * Enable the shortcuts switch.
     */
    public void enableShortcuts() {
        if (!aSwitch.isEnabled()) {
            aSwitch.toggle();
        }
    }

    /**
     * Disable the shortcuts switch.
     */
    public void disableShortcuts() {
        if (aSwitch.isEnabled()) {
            aSwitch.toggle();
        }
    }

    /**
     * Press "Save" button to save the changes.
     */
    public void save() {
        final SelenideElement currentElement = element();
        currentElement.$("button[variant=\"primary\"]").should(EXISTS_ENABLED_VISIBLE).click();
        currentElement.shouldNotBe(Condition.visible);
    }

    /**
     * Check if the tooltip "Shortcuts are disabled" is visible.
     *
     * @return true if tooltip is visible.
     */
    public boolean isTooltipVisible() {
        final String selector = String.format("%s coral-alert.granite-shortcuts-dialog-enable-alert", getCssSelector());
        Helpers.waitForElementAnimationFinished(selector);
        return $(selector).is(Condition.visible);
    }
}
