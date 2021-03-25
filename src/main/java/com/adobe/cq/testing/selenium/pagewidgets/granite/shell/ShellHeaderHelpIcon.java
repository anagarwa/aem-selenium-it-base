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
import com.adobe.cq.testing.selenium.pagewidgets.I18N;
import com.adobe.cq.testing.selenium.Constants;
import com.codeborne.selenide.Condition;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.codeborne.selenide.Selenide.$;

public final class ShellHeaderHelpIcon extends ShellMenuBarItem {

    /**
     * Construct the ShellHeaderHelpIcon.
     */
    public ShellHeaderHelpIcon() {
        super(String.format("coral-shell-menubar-item[title=\"%s\"]", I18N.geti18nString("Help")));
    }

    /**
     * @return ShellHelpComponent.
     */
    public ShellHelp open() {
        element().should(Constants.EXISTS_ENABLED_VISIBLE).click();
        $("coral-shell-menu").should(Constants.EXISTS_ENABLED_VISIBLE).should(Condition.cssClass("is-open"));
        Helpers.waitForElementAnimationFinished("coral-shell-menu");
        final ShellHelp help = new ShellHelp();
        help.waitVisible();
        return help;
    }
}
