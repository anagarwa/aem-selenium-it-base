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

import com.adobe.cq.testing.selenium.pageobject.cq.sites.CreateTemplateWizard;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.clickDialogAction;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * The template console page
 */
public class TemplateConsolePage extends CollectionPage {

    private static final String CONSOLE_BASE_PATH = "/libs/wcm/core/content/sites/templates.html";
    private static final String CONSOLE_PAGE_PATH = CONSOLE_BASE_PATH + "/conf";
    private static final String CREATE_TEMPLATE_BUTTON_CSS = "button.cq-siteadmin-admin-createtemplate-activator";

    /**
     * Construct a TemplateConsolePage.
     */
    public TemplateConsolePage() {
        super(CONSOLE_PAGE_PATH);
    }
    public TemplateConsolePage(String templateFolderPath) {
        super(CONSOLE_BASE_PATH + templateFolderPath);
    }

    @Override
    public TemplateConsolePage open(String templateFolderPath) { return (TemplateConsolePage) super.open(CONSOLE_BASE_PATH + templateFolderPath); }

    public SelenideElement getCreateButton() { return $(CREATE_TEMPLATE_BUTTON_CSS); }

    public CreateTemplateWizard openCreateTemplateWizard() {

        // Click on create action
        final SelenideElement createAction = getCreateButton();
        ExpectNav.on(() -> clickableClick(createAction));

        CreateTemplateWizard wizard = new CreateTemplateWizard(getTemplateFolderPath());
        wizard.waitReady();
        return wizard;
    }

    public TemplateConsolePageToolbarActions actions() {
        return new TemplateConsolePageToolbarActions();
    }

    public String getTemplateFolderPath() {
        String currentPath = this.getPath();
        return currentPath.substring(currentPath.indexOf(CONSOLE_BASE_PATH) + CONSOLE_BASE_PATH.length());
    }

    public static final class TemplateConsolePageToolbarActions {

        private TemplateConsolePageToolbarActions() {}

        // Template console action bar items
        private static SelenideElement edit = $("button.cq-siteadmin-admin-actions-edit-activator");
        private static SelenideElement properties = $("button.cq-siteadmin-admin-actions-properties-activator");
        private static SelenideElement enable = $("button.cq-siteadmin-admin-actions-enable-activator");
        private static SelenideElement disable = $("button.cq-siteadmin-admin-actions-disable-activator");
        private static SelenideElement publish = $("button.cq-siteadmin-admin-actions-publish-activator");
        private static SelenideElement copy = $("button.cq-siteadmin-admin-actions-copy-activator");
        private static SelenideElement delete = $("button.cq-siteadmin-admin-actions-delete-activator");
        private static ActionComponent<Dialog> deleteButton = new ActionComponent(delete, () -> new Dialog(), false);
        private static ActionComponent<Dialog> enableButton = new ActionComponent(enable, () -> new Dialog(), false);
        private static ActionComponent<Dialog> disableButton = new ActionComponent(disable, () -> new Dialog(), false);


        public static SelenideElement getEdit() {
            return edit;
        }
        public static SelenideElement getProperties() {
            return properties;
        }
        public static SelenideElement getEnable() {
            return enable;
        }
        public static SelenideElement getPublish() {
            return publish;
        }
        public static SelenideElement getCopy() {
            return copy;
        }
        public static SelenideElement getDelete() {
            return delete;
        }

        public static Dialog clickDelete() { return clickDialogAction(deleteButton); }
        public static Dialog clickEnable() { return clickDialogAction(enableButton); }
        public static Dialog clickDisable() { return clickDialogAction(disableButton); }
    }
}
