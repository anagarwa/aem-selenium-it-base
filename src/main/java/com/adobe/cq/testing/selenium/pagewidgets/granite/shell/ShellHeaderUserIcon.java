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

package com.adobe.cq.testing.selenium.pagewidgets.granite.shell;

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.granite.UserProperties;
import com.adobe.cq.testing.selenium.Constants;
import com.codeborne.selenide.Condition;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.codeborne.selenide.Selenide.$;

/**
 * Case-study: An AEM page specific component that needs it's own abstraction
 * over the Coral Component class.
 *
 * Because this component is important in the AEM page template and has
 * an 'open' behaviour attached to it, it needs it's own abstraction.
 *
 * The internals of ShellMenuBarItem are getting overwritten in the process,
 * because the ShellMenu that this icon opens is different in content than a
 * plain Coral ShellMenu Component. It's AEM specific.
 *
 * In this case the ShellMenu class is used as a guideline to write AEMShellMenu.
 * And so for their respective child classes.
 */
public final class ShellHeaderUserIcon extends ShellMenuBarItem {

    /**
     * Construct a ShellHeaderUserIcon.
     */
    public ShellHeaderUserIcon() {
        super("coral-shell-menubar-item[icon=\"userCircleColor\"]");
    }

    /**
     * @return UserPropertiesComponent.
     */
    public UserProperties open() {
        element().should(Constants.EXISTS_ENABLED_VISIBLE).click();
        $("coral-shell-menu").should(Constants.EXISTS_ENABLED_VISIBLE).should(Condition.cssClass("is-open"));
        Helpers.waitForElementAnimationFinished("coral-shell-menu");
        return new UserProperties();
    }
}
