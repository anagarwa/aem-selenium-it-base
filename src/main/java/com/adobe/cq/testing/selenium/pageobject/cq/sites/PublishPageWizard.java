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


package com.adobe.cq.testing.selenium.pageobject.cq.sites;

import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$$;

public class PublishPageWizard extends Wizard {

    private final static String SELECT_ALL_CHECKBOXES = "thead coral-checkbox input";

    public PublishPageWizard selectAll() { return toggleAllCheckboxes(true); }
    public PublishPageWizard deselectAll() { return toggleAllCheckboxes(false); }

    private PublishPageWizard toggleAllCheckboxes(boolean select) {
        ElementsCollection selectAllList = $$(SELECT_ALL_CHECKBOXES);
        for (SelenideElement selectAllItem : selectAllList) {
            if ((select && !selectAllItem.isSelected()) || (!select && selectAllItem.isSelected())) {
                clickableClick(selectAllItem);
            }
        }
        return this;
    }
}
