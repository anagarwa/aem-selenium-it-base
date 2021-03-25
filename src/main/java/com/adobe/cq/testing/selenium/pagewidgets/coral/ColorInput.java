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
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ColorInput extends BaseComponent {

    /**
     * @param cssSelector the selector for wrapping this on.
     */
    public ColorInput(final String cssSelector) {
        super(cssSelector);
    }

    /**
     * @return the input value.
     */
    public String getValue() {
        return getInput().val();
    }

    /**
     * @return true if required attribute is present.
     */
    public boolean isRequired() {
        return getInput().has(Condition.attribute("required"));
    }

    /**
     * @return input text label.
     */
    public String getLabelText() {
        return element().parent().$("label").getText();
    }

    /**
     * @return true if it as a description / tooltip.
     */
    public boolean hasDescription() {
        final CoralIcon icon = new CoralIcon(
            String.format("%s + coral-icon", getCssSelector()));
        icon.render();

        final String tooltipSelector = String.format("%s + coral-icon + coral-tooltip", getCssSelector());
        return icon.isVisible() && element().$(tooltipSelector).exists();
    }

    /**
     * @return the tooltip content.
     */
    public String getDescriptionText() {
        final String tooltipContentSelector = String.format("%s + coral-icon + coral-tooltip > coral-tooltip-content", getCssSelector());
        return element().$(tooltipContentSelector).innerHtml();
    }

    private SelenideElement getInput() {
        return $(String.format("%s > input", getCssSelector()));
    }
}
