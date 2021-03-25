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


package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.coral.VariantAccessor.VariantType.DEFAULT;
import static com.adobe.cq.testing.selenium.pagewidgets.coral.VariantAccessor.VariantType.PRIMARY;
import static com.codeborne.selenide.Selenide.$;

public class StartWorkflowDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog";
    private static final String WF_SELECT = "name='model'";
    private static final String WF_TITLE = "input[name='workflowTitle']";

    public StartWorkflowDialog() { super(CSS_SELECTOR); }

    public CoralSelect wfSelect = new CoralSelect(WF_SELECT);

    public ElementsCollection getWorkflowList() {
        return wfSelect.items();
    }

    public void selectWorkflow(String wfResourcePath) { wfSelect.selectItemByValue(wfResourcePath); }

    public void setWorkflowTitle(String wfTitle) { $(WF_TITLE).setValue(wfTitle); }

    public SelenideElement getWorkflowSelectElement() { return wfSelect.element(); }

    public void startWorkflow() {
        super.clickPrimary();
        element().shouldNotBe(Condition.visible);
    }

    public void close() {
        super.clickDefault();
        element().shouldNotBe(Condition.visible);
    }

    public SelenideElement startWorkflowButton() { return variantAccessor.find(PRIMARY); }
    public SelenideElement closeButton() { return variantAccessor.find(DEFAULT); }

}
