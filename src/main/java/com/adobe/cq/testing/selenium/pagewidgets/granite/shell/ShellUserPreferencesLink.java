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
import com.adobe.cq.testing.selenium.pagewidgets.granite.UserPreferences;
import com.adobe.cq.testing.selenium.Constants;

public final class ShellUserPreferencesLink extends BaseComponent {

    /**
     * @param selector The full CSS selector that leads to the HTML element.
     */
    public ShellUserPreferencesLink(final String selector) {
        super(selector);
    }

    /**
     * @return UserPreferencesComponent.
     */
    public UserPreferences open() {
        element().should(Constants.EXISTS_ENABLED_VISIBLE).click();
        UserPreferences pref = new UserPreferences();
        pref.waitVisible();
        return pref;
    }

}
