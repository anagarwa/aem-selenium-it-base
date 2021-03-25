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
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.codeborne.selenide.Selenide.$;

public final class UserPreferences extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog";
    private static final String SHORTCUTS_ENABLED = "%s coral-checkbox[name=\"shortcutsEnabled\"]";
    private static final String CHECKED = "checked";

    /**
     * Construct a UserPreferences Component.
     */
    public UserPreferences() {
        super(CSS_SELECTOR);
    }

    /**
     * The current behaviour is a whole page refresh.
     */
    public void save() {
        final SelenideElement currentElement = element();
        currentElement.$("button[variant=\"primary\"]").should(EXISTS_ENABLED_VISIBLE).click();
        currentElement.shouldNot(Condition.visible);
    }

    /**
     * click on cancel button.
     */
    public void cancel() {
        final SelenideElement currentElement = element();
        currentElement.$("button[coral-close]").should(EXISTS_ENABLED_VISIBLE).click();
        currentElement.shouldNot(Condition.visible);
    }

    /**
     * @return true if this dialog is closed.
     */
    public boolean isClosed() {
        return !element().exists();
    }

    /**
     * @return true is shortcutsEnabled is checked.
     */
    public boolean shortcutsAreEnabled() {
        // @todo Add CheckboxComponent
        final String selector = String.format(SHORTCUTS_ENABLED, getCssSelector());
        return $(selector).getAttribute(CHECKED) != null;
    }

    /**
     * Client on enableShortcuts checkbox.
     */
    public void enableShortcuts() {
        // @todo Add CheckboxComponent
        final String selector = String.format(SHORTCUTS_ENABLED, getCssSelector());
        final boolean isEnabled = $(selector).should(EXISTS_ENABLED_VISIBLE).getAttribute(CHECKED) != null;
        if (!isEnabled) {
            $(selector).click();
        }
    }

    /**
     * Click to disable shortcuts.
     */
    public void disableShortcuts() {
        // @todo Add CheckboxComponent
        final String selector = String.format(SHORTCUTS_ENABLED, getCssSelector());
        final boolean isEnabled = $(selector).should(EXISTS_ENABLED_VISIBLE).getAttribute(CHECKED) != null;
        if (isEnabled) {
            $(selector).click();
        }
    }

    /**
     * Check if the shortcuts switch is checked.
     * @return true if switch is checked.
     */
    public boolean isShortcutsSwitchChecked() {
        final String selector = String.format(SHORTCUTS_ENABLED, getCssSelector());
        return $(selector).should(EXISTS_ENABLED_VISIBLE).getAttribute(CHECKED) != null;
    }

    /**
     * Toggle the shortcuts switch.
     */
    public void toggleEnableShortcuts() {
        element().$("coral-checkbox[name='shortcutsEnabled']").should(EXISTS_ENABLED_VISIBLE).click();
    }

    /**
     * @param lang which language to switch to.
     */
    public void changeLanguageTo(final String lang) {
        final String  cssSel = getCssSelector() + " coral-select[name='language']";
        Helpers.waitForElementAnimationFinished(cssSel);
        $(cssSel).should(EXISTS_ENABLED_VISIBLE).click();
        Helpers.waitForElementAnimationFinished(cssSel);
        $(cssSel).$("coral-selectlist-item[value=\"" + lang + "\"]").should(EXISTS_ENABLED_VISIBLE).click();
    }
}
