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

import com.adobe.cq.testing.selenium.Constants;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Switch extends BaseComponent {

    /**
     * @param cssSelector to wrap it on.
     */
    public Switch(final String cssSelector) {
        super(cssSelector);
    }

    /**
     * @param element to wrap it on.
     */
    public Switch(final SelenideElement element) {
        super(element);
    }

    /**
     * @return true if the "checked" attribute is present.
     */
    public boolean isEnabled() {
        return element().getAttribute("checked") != null;
    }

    /**
     * @return the defined label text.
     */
    public String getLabelText() {
        return $(String.format("%s + label", getCssSelector())).getText();
    }

    /**
     * Click on this element.
     */
    public void toggle() {
        element().shouldBe(Constants.EXISTS_ENABLED_VISIBLE).click();
    }

}
