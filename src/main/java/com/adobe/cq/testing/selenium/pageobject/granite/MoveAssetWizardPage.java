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

package com.adobe.cq.testing.selenium.pageobject.granite;

import com.adobe.cq.testing.selenium.Constants;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;

public class MoveAssetWizardPage extends Wizard {

    private static final String RENAME_BUTTON = ".cq-damadmin-admin-actions-move-activator";
    private static final String ACTIONBAR_MORE = ".foundation-collection-actionbar button[coral-actionbar-more]";
    private static final String MOVE_ASSET_WIZARD = ".cq-damadmin-admin-moveasset-rename";
    private static final String THUMBNAIL = "coral-columnview-item:nth-child(1) coral-columnview-item-thumbnail ";

    private boolean wizardInitialized;

    /**
     * Default constructor.
     */
    public MoveAssetWizardPage() {
        super();
        wizardInitialized = false;
    }

    /**
     * Opens a wizard for provided asset.
     */
    public void initWizard() {
        // @todo: abstract actions from an action bar
        if ($(RENAME_BUTTON).isDisplayed()) {
            $(RENAME_BUTTON).click();
        } else {
            // the action has been collapsed, that means that we need to click the more button and select the action
            // from the popover
            $(ACTIONBAR_MORE).shouldBe(Constants.EXISTS_ENABLED_VISIBLE).click();
            $(RENAME_BUTTON).shouldBe(Constants.EXISTS_ENABLED_VISIBLE).click();
        }
        $(MOVE_ASSET_WIZARD).shouldBe(Constants.EXISTS_ENABLED_VISIBLE);
        wizardInitialized = true;
    }

    /**
     * Attempts to move to next step.
     */
    public void proceedToNextStep() {
        if (wizardInitialized) {
            next();
            $(THUMBNAIL).shouldBe(Condition.visible);
        }
    }
}
