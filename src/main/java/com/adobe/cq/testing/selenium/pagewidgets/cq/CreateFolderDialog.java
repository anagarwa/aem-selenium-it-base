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

import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.*;

public final class CreateFolderDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog";

    private static final SelenideElement TITLE_FIELD_SELECTOR = TITLE.getFullyDecoratedElement("input");
    private static final SelenideElement NAME_FIELD_SELECTOR = OP_NAME.getFullyDecoratedElement("input");
    private static final SelenideElement ORDERABLE_FIELD_SELECTOR = PRIMARY_TYPE.getFullyDecoratedElement("coral-checkbox");

    /**
     * Construct a UserPreferences Component.
     */
    public CreateFolderDialog() {
        super(CSS_SELECTOR);
    }

    /**
     * @return title field.
     */
    public SelenideElement title() {
        return TITLE_FIELD_SELECTOR;
    }

    /**
     * @return the name field.
     */
    public SelenideElement name() {
        return NAME_FIELD_SELECTOR;
    }

    /**
     * @return true if the orderable field is currently checked.
     */
    public boolean isOrderable() {
        return ORDERABLE_FIELD_SELECTOR.getAttribute("checked") != null;
    }

    /**
     * Check the orderable checkbox (if not yet checked).
     */
    public void checkOrderable() {
        ORDERABLE_FIELD_SELECTOR.should(EXISTS_ENABLED_VISIBLE);
        if (!isOrderable()) {
            ORDERABLE_FIELD_SELECTOR.click();
        }
    }

    /**
     * Uncheck the orderable checkbox (if currently checked).
     */
    public void uncheckOrderable() {
        ORDERABLE_FIELD_SELECTOR.should(EXISTS_ENABLED_VISIBLE);
        if (isOrderable()) {
            ORDERABLE_FIELD_SELECTOR.click();
        }
    }
}
