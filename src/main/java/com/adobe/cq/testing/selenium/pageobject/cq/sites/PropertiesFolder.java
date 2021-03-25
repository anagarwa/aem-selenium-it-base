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

import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.TITLE;
import static com.codeborne.selenide.Selenide.$;

/**
 * The PropertiesFolder page object.
 */
public class PropertiesFolder extends BasePage {

    private final SelenideElement cancelElement;
    private final SelenideElement doneElement;

    /**
     * Construct the page properties page.
     * @param pagePath of the page to open the properties on.
     */
    public PropertiesFolder(final String pagePath) {
        super("/mnt/overlay/wcm/core/content/sites/folderproperties.html" + pagePath);
        cancelElement = $("#shell-propertiespage-closeactivator");
        doneElement = $("#shell-propertiespage-doneactivator");
    }

    /**
     * Click on the cancel button.
     */
    public void cancel() {
        cancelElement.should(EXISTS_ENABLED_VISIBLE).click();
    }

    /**
     * Click on the save and close button.
     */
    public void save() {
        doneElement.should(EXISTS_ENABLED_VISIBLE).click();
    }


    /**
     * @return available fields for folder properties.
     */
    public Fields fields() {
        return new Fields();
    }

    public final class Fields {

        // Define some common fields
        private Fields() {

        }

        /**
         * @return the title field input element.
         */
        public SelenideElement title() {
            return TITLE.getFullyDecoratedElement("input");
        }

        /**
         * @return the checkbox input.
         */
        public CoralCheckbox orderable() {
            return new CoralCheckbox("label.coral-Checkbox");
        }

    }
}
