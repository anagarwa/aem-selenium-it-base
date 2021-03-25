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


package com.adobe.cq.testing.selenium.pagewidgets.cq.tabs;

import com.adobe.cq.testing.selenium.pagewidgets.DesignPicker;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

public final class AdvancedTab extends BaseComponent {
    private SelenideElement designFieldButton = $(FormField.DESIGN_PATH + " button");

    /**
     * Construct a wrapper on AdvancedTab panel content.
     * @param panelId the associated element id.
     */
    public AdvancedTab(final String panelId) {
        super("#" + panelId);
    }

    public DesignPicker openDesignPicker() {
        clickableClick(designFieldButton);
        DesignPicker designPicker = new DesignPicker();
        designPicker.getDesignPickerMainElement().shouldBe(Condition.exist, Condition.visible);
        return designPicker;
    }
}
