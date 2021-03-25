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

package com.adobe.cq.testing.selenium.pageobject.cq.conf;

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.DESCRIPTION;
import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.TITLE;

public class CFModelCreateWizard extends Wizard {

    private static final String CREATE_MODEL_BASE_URL = "/mnt/overlay/dam/cfm/models/console/content/createmodelwizard.html";
    private static final String SELECTOR_WIZARD_STATUS_ENABLED = "coral-checkbox[name=\"isStatusEnabled\"]";
    private static final SelenideElement TITLE_FIELD = TITLE.getFullyDecoratedElement("input");
    private static final SelenideElement DESCRIPTION_FIELD = DESCRIPTION.getFullyDecoratedElement("textarea");
    private final AutoCompleteField<Picker> tags = new AutoCompleteField<Picker>("./cq:tags", () -> new Picker("coral-dialog"), false);
    
    private static final Dialog CONFIRM_DIALOG = new Dialog("coral-dialog[variant=\"success\"]");

    public boolean isOpened() {
        return WebDriverRunner.url().contains(CREATE_MODEL_BASE_URL);
    }

    public SelenideElement title() {
        return TITLE_FIELD;
    }

    public SelenideElement description() {
        return DESCRIPTION_FIELD;
    }

    public CoralCheckbox enabled() {
        return new CoralCheckbox(element().$(SELECTOR_WIZARD_STATUS_ENABLED).should(Condition.exist).getSearchCriteria());
    }
    
    public Dialog confirmDialog() {
        return CONFIRM_DIALOG;
    }
    
    public AutoCompleteField<Picker> tags() {
        return tags;
    }

}
