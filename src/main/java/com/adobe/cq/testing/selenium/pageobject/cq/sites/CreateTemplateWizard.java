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

import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Masonry;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

public class CreateTemplateWizard extends Wizard {

    private static final String CREATE_WIZARD_URL = "/libs/wcm/core/content/sites/createtemplatewizard.html%s";
    public static final String HTML5_PAGE = "/libs/settings/wcm/template-types/html5page";

    private static final Collection COLLECTION = new Masonry(".foundation-collection");
    private static final SelenideElement TITLE_FIELD = new FormField("./jcr:title")
            .getFullyDecoratedElement("coral-panel.is-selected input");
    private static final SelenideElement DESCRIPTION_FIELD = new FormField("./jcr:description")
            .getFullyDecoratedElement("coral-panel.is-selected textarea");
    private static final Dialog CONFIRM_DIALOG = new Dialog("coral-dialog");

    private final String getCreateWizardUrl;

    public CreateTemplateWizard(String templateFolderPath) {
        getCreateWizardUrl = String.format(CREATE_WIZARD_URL, templateFolderPath);
    }

    /**
     * @return true if the wizard is opened.
     */
    public boolean isOpened() {
        return WebDriverRunner.url().contains(getCreateWizardUrl);
    }

    /**
     * @param templateTypeId the id of the template type (path in apps or libs usually).
     */
    public void selectTemplateType(final String templateTypeId) {
        COLLECTION.selectItem(templateTypeId);
        COLLECTION.waitVisible();
    }

    /**
     * @return the template title field.
     */
    public SelenideElement templateTitle() {
        return TITLE_FIELD;
    }

    /**
     * @return the template description field.
     */
    public SelenideElement templateDescription() {
        return DESCRIPTION_FIELD;
    }

    /**
     * @return the confirm dialog displayed at last step.
     */
    public Dialog confirmDialog() {
        return CONFIRM_DIALOG;
    }

}
