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

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.ASSET_FOLDER_TITLE;
import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.OP_NAME;

public final class CreateDAMFolderDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog";

    private static final SelenideElement TITLE_FIELD_SELECTOR = ASSET_FOLDER_TITLE.getFullyDecoratedElement("input");
    private static final SelenideElement NAME_FIELD_SELECTOR = OP_NAME.getFullyDecoratedElement("input");
    private static final CoralCheckbox PRIVATE_FIELD_SELECTOR = new CoralCheckbox("dialog.is-open coral-checkbox:eq(0)");
    private static final CoralCheckbox ORDERABLE_FIELD_SELECTOR = new CoralCheckbox("dialog.is-open coral-checkbox:eq(1)");

    /**
     * Construct a UserPreferences Component.
     */
    public CreateDAMFolderDialog() {
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
        return ORDERABLE_FIELD_SELECTOR.isChecked();
    }

    /**
     * @return true if the private field is currently checked.
     */
    public boolean isPrivate() {
        return PRIVATE_FIELD_SELECTOR.isChecked();
    }

    /**
     * Check the orderable checkbox (if not yet checked).
     */
    public void checkOrderable() {
        ORDERABLE_FIELD_SELECTOR.waitVisible();
        if (!isOrderable()) {
            ORDERABLE_FIELD_SELECTOR.click();
        }
    }

    /**
     * Uncheck the orderable checkbox (if currently checked).
     */
    public void uncheckOrderable() {
        ORDERABLE_FIELD_SELECTOR.waitVisible();
        if (isOrderable()) {
            ORDERABLE_FIELD_SELECTOR.click();
        }
    }

    /**
     * Check the private checkbox (if not yet checked).
     */
    public void checkPrivate() {
        PRIVATE_FIELD_SELECTOR.waitVisible();
        if (!isPrivate()) {
            PRIVATE_FIELD_SELECTOR.click();
        }
    }

    /**
     * Uncheck the private checkbox (if currently checked).
     */
    public void uncheckPrivate() {
        PRIVATE_FIELD_SELECTOR.waitVisible();
        if (isPrivate()) {
            PRIVATE_FIELD_SELECTOR.click();
        }
    }
}
