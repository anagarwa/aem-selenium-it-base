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

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.SelenideElement;

public class CoralIcon extends BaseComponent {

    /**
     * A default alert icon.
     */
    public static final CoralIcon ALERT_ICON = new CoralIcon("coral-icon[icon=\"alert\"]");


    /**
     * @param cssSelector css selector.
     */
    public CoralIcon(final String cssSelector) {
        super(cssSelector);
    }


    /**
     * @param parent define the parent for this CoralIcon.
     * @param iconName the icon to find in the parent subtree.
     */
    public CoralIcon(final SelenideElement parent, final String iconName) {
        super(parent.find("coral-icon[icon=\"" + iconName + "\"]"));
    }

    /**
     * Get the icon's text: add, bell, star, etc.
     *
     * @return the icon label as text.
     */
    public String getIconLabelText() {
        return element().getAttribute("icon");
    }

    /**
     * Get the icon's size: EXTRA_SMALL, SMALL, MEDIUM, LARGE.
     *
     * @return the icon size as text.
     */
    public String getSizeLabelText() {
        return element().getAttribute("size");
    }

}
