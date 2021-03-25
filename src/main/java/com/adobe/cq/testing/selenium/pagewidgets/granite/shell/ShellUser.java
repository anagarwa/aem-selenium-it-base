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

public final class ShellUser extends BaseComponent {

    /**
     * @param selector The full CSS selector that leads to the HTML element.
     */
    public ShellUser(final String selector) {
        super(selector);
    }

    /**
     * @return username.
     */
    public String getUsername() {
        return element().$("coral-shell-user-heading").getText();
    }

    /**
     * @return a ShellUserSubheadingComponent.
     */
    public ShellUserSubheading subheading() {
        final String selector = String.format("%s coral-shell-user-subheading", getCssSelector());
        return new ShellUserSubheading(selector);
    }

    /**
     * @return a ShellUserFooterComponent.
     */
    public ShellUserFooter footer() {
        final String selector = String.format("%s coral-shell-user-footer", getCssSelector());
        return new ShellUserFooter(selector);
    }
}
