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

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.SelenideElement;

public final class ShellHeader extends BaseComponent {

    /**
     * Construct a ShellHeaderComponent.
     */
    public ShellHeader() {
        super("coral-shell-header");
    }

    /**
     * @return the user icon element.
     */
    public SelenideElement userIconElement() {
        return element().$("coral-shell-menubar-item[icon=\"userCircleColor\"]");
    }

    /**
     * @return the help button element.
     */
    public SelenideElement helpButton() {
        return element().$("coral-shell-menubar-item[icon=\"helpCircle\"]");
    }

    /**
     * @return get the related ShellHeaderActionsComponent.
     */
    public ShellHeaderActions actions() {
        final String selector = String.format("%s > coral-shell-header-actions", getCssSelector());
        return new ShellHeaderActions(selector);
    }
}
