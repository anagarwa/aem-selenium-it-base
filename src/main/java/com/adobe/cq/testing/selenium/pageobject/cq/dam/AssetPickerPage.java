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

package com.adobe.cq.testing.selenium.pageobject.cq.dam;

import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.codeborne.selenide.Selenide.$;

/**
 * The assets main page.
 */
public class AssetPickerPage extends BasePage {
    /**
     * Default constructor.
     */
    public AssetPickerPage() {
        super("/aem/assetpicker.html");
    }

    /**
     * Open assetpicker page.
     */
    @Override
    public AssetPickerPage open() {
        super.open();
        $(".granite-pickerdialog-content.foundation-form").shouldBe(EXISTS_ENABLED_VISIBLE);
        return this;
    }

    /**
     * Get the searchField element.
     *
     * @return searchField element.
     */
    public SelenideElement searchField() {
        final String selector = ".foundation-layout-panel-header .granite-pickerdialog-searchfield input";
        return $(selector).shouldBe(EXISTS_ENABLED_VISIBLE);
    }

    /**
     * Search in assetpicker for a specific search text.
     *
     * @param searchText - The text for searching.
     */
    public void search(final String searchText) {
        searchField().setValue(searchText + "\uE007");
        $("#cq-damadmin-admin-assetselector-search-collection").shouldBe(EXISTS_ENABLED_VISIBLE);
    }
}
