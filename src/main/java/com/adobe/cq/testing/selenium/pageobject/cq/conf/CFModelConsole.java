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

import com.adobe.cq.testing.selenium.pageobject.granite.CollectionPage;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.SelenideElement;
import com.adobe.cq.testing.selenium.pagewidgets.coral.VariantAccessor;

import javax.annotation.Nonnull;

import static com.adobe.cq.testing.selenium.pagewidgets.coral.VariantAccessor.VariantType;
import static com.codeborne.selenide.Selenide.$;

/**
 * Object representing the Content Fragment Model console page at:
 */
public class CFModelConsole extends CollectionPage {

    private static final String BASE_PATH = "/libs/dam/cfm/models/console/content/models.html";
    private static final String CF_MODEL_TITLE_SELECTOR = ".foundation-collection-item-title[value=\"%s\"]";
    private static final String CF_MODEL_ITEM_SELECTOR = ".foundation-collection-item";

    public CFModelConsole() {
        super(BASE_PATH);
    }

    public CFModelConsole(String confPath) {
        super(String.format("%s%s", BASE_PATH, confPath));
    }

    @Override
    public CFModelConsole open(@Nonnull String path) {
        super.open(String.format("%s%s", BASE_PATH, path));
        return this;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public CFModelConsole open() {
        super.open();
        return this;
    }

    public CFModelConsoleToolbarActions actions() {
        return new CFModelConsoleToolbarActions(this);
    }

    public CFModel getModelByTitle(String title) {
        SelenideElement titleEl = $(String.format(CF_MODEL_TITLE_SELECTOR, title));
        SelenideElement model = titleEl.closest(CF_MODEL_ITEM_SELECTOR);
        return new CFModel(model);
    }

    // Helper class "CF Model" on CF model console page
    public static final class CFModel {
        private static final String CF_MODEL_SELECTOR = "[data-foundation-collection-item-id=\"%s\"]";
        private static final String ATTRIBUTE_COLLECTION_ITEM_ID = "data-foundation-collection-item-id";
        private VariantAccessor variantAccessor;
        private SelenideElement model;

        public CFModel(String cfModelPath) {
            model = $(String.format(CF_MODEL_SELECTOR, cfModelPath));
            variantAccessor = new VariantAccessor(model);
        }

        public CFModel(SelenideElement modelEl) {
            model = modelEl;
            variantAccessor = new VariantAccessor(model);
        }

        public Boolean exists() {
            return model.exists();
        }

        public Boolean hasAlert(VariantType variantType) {
            return variantAccessor.findAll(variantType, "coral-alert").first().exists();
        }

        public String getId() {
           return model.attr(ATTRIBUTE_COLLECTION_ITEM_ID);
        }
        
        public Boolean isStatusEnabled() {
            return hasAlert(VariantType.SUCCESS);
        }
        public Boolean isStatusDraft() {
            return hasAlert(VariantType.WARNING);
        }
        public Boolean isStatusDisabled() {
            return hasAlert(VariantType.ERROR);
        }
    }

    // Helper Classes for CF Models Console
    public static final class CFModelConsoleToolbarActions {
        private CFModelConsole modelConsole;
        private CFModelConsoleToolbarActions(CFModelConsole modelConsole) {
            this.modelConsole = modelConsole;
        }

        private static final SelenideElement create = $(".cq-cfm-models-createmodel-activator");
        private static final SelenideElement edit = $(".cq-cfm-models-edit-activator");
        private static final SelenideElement properties = $(".cq-cfm-models-properties-activator");
        private static final SelenideElement copy = $(".cq-cfm-models-copy-activator");
        private static final SelenideElement paste = $(".cq-cfm-paste-activator");
        private static final SelenideElement delete = $(".cq-cfm-models-delete-activator");
        private static final SelenideElement publish = $(".cq-cfm-models-publish-activator");
        private static final SelenideElement unpublish = $(".cq-cfm-models-unpublish-activator");
        private static final SelenideElement enable = $(".cq-cfm-models-enable-activator");
        private static final SelenideElement disable = $(".cq-cfm-models-disable-activator");

        public ActionComponent<CFModelCreateWizard> create() {
            return new ActionComponent<>(create, CFModelCreateWizard::new, true);
        }

        public ActionComponent<CFModelEditor> edit() {
            final String selectedModelPath = this.modelConsole.collection().getSelectedItems()
                    .first().attr("data-foundation-collection-item-id");
            return new ActionComponent<>(edit, () -> new CFModelEditor().open(selectedModelPath), true);
        }

        public ActionComponent<CFModelPropertiesPage> properties() {
            return new ActionComponent<>(properties, CFModelPropertiesPage::new, true);
        }

        public ActionComponent<Dialog> copy() {
            return new ActionComponent<>(copy, Dialog::new, false);
        }
        
        public ActionComponent<Dialog> paste() {
            return new ActionComponent<>(paste, Dialog::new, false);
        }

        public ActionComponent<Dialog> delete() {
            return new ActionComponent<>(delete, Dialog::new, false);
        }

        public ActionComponent<Dialog> publish() {
            return new ActionComponent<>(publish, Dialog::new, false);
        }
        
        public ActionComponent<Dialog> unpublish() {
            return new ActionComponent<>(unpublish, Dialog::new, false);
        }

        public ActionComponent<Dialog> enable() {
            return new ActionComponent<>(enable, Dialog::new, false);
        }

        public ActionComponent<Dialog> disable() {
            return new ActionComponent<>(disable, Dialog::new, false);
        }

    }

}
