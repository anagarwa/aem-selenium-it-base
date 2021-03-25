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

public final class DeleteFolderDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog";
    private static final String ARCHIVE_FIELD_SELECTOR = "coral-checkbox[name=\"archive\"]";

    private final SelenideElement archiveField;

    /**
     * Construct a UserPreferences Component.
     */
    public DeleteFolderDialog() {
        super(CSS_SELECTOR);
        archiveField = element().$(ARCHIVE_FIELD_SELECTOR);
    }

    /**
     * @return true if the archive field is currently checked.
     */
    public boolean isArchive() {
        return archiveField.getAttribute("checked") != null;
    }

    /**
     * Check the archive checkbox (if not yet checked).
     */
    public void checkArchive() {
        archiveField.should(EXISTS_ENABLED_VISIBLE);
        if (!isArchive()) {
            archiveField.click();
        }
    }

    /**
     * Uncheck the archive checkbox (if currently checked).
     */
    public void uncheckArchive() {
        archiveField.should(EXISTS_ENABLED_VISIBLE);
        if (isArchive()) {
            archiveField.click();
        }
    }
}
