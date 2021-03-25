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
import com.adobe.cq.testing.selenium.pageobject.granite.ViewType;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.Arrays;
import java.util.Iterator;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * Object representing the Configuration Browser page
 */
public class ConfigBrowser extends CollectionPage {

    private static final String BASE_PATH = "/libs/granite/configurations/content/view.html/conf";

    public ConfigBrowser() {
        super(BASE_PATH);
        setCurrentView(ViewType.COLUMN);
    }

    public ConfigBrowserToolbarActions actions() {
        return new ConfigBrowserToolbarActions();
    }

    public ConfigBrowserCreateDialog create() {
        clickableClick($("button.granite-collection-create"));
        return new ConfigBrowserCreateDialog().waitVisible();
    }

    // Helper Classes for CF Models Console
    public static final class ConfigBrowserToolbarActions {
        private ConfigBrowserToolbarActions() {
        }

        private static final SelenideElement properties = $(".conf-browser-edit-activator");
        private static final SelenideElement delete = $(".conf-browser-delete-activator");
        private static final SelenideElement publish = $(".conf-browser-delete-activator");

        public ActionComponent<ConfigPropertiesPage> properties() {
            return new ActionComponent<>(properties, ConfigPropertiesPage::new, false);
        }

        public ActionComponent<Dialog> delete() {
            return new ActionComponent<>(delete, Dialog::new, false);
        }

        public ActionComponent<Dialog> publish() {
            return new ActionComponent<>(publish, Dialog::new, false);
        }
    }

    public static final class ConfigBrowserCreateDialog extends Dialog {

        private static final SelenideElement CONF_TITLE_FIELD = new FormField("configTitle").getFullyDecoratedElement("input");
        private static final SelenideElement CONF_NAME_FIELD = new FormField("configName").getFullyDecoratedElement("input");
        private static final SelenideElement CONF_CAPABILITIES = new FormField("configCapabilities").getFullyDecoratedElement("coral-checkbox");

        public SelenideElement title() {
            return element().find(CONF_TITLE_FIELD.getSearchCriteria());
        }

        public SelenideElement name() {
            return element().find(CONF_NAME_FIELD.getSearchCriteria());
        }

        public boolean hasCapabilities(ConfigCapabilities ...capability) {
            boolean hasIt = false;
            Iterator<ConfigCapabilities> iterator = Arrays.stream(capability).iterator();
            while (!hasIt && iterator.hasNext()) {
                ConfigCapabilities cap = iterator.next();
                hasIt = !element().findAll(CONF_CAPABILITIES.getSearchCriteria()).filter(Condition.checked).filter(Condition.attribute("value", cap.value())).isEmpty();
            }
            return hasIt;
        }

        public ConfigBrowserCreateDialog checkCapabilities(ConfigCapabilities ...capability) {
            ElementsCollection toCheck = element().findAll(CONF_CAPABILITIES.getSearchCriteria());
            for (ConfigCapabilities cap : capability) {
                ElementsCollection value = toCheck.filter(Condition.attribute("value", cap.value()));
                value.forEach(e -> clickableClick(e));
            }
            return this;
        }
    }

    public enum ConfigCapabilities {
        CLOUD_CONFIGS("Cloud Configurations"),
        CONTEXT_HUB("ContextHub Segments"),
        CONTENT_FRAGMENTS("Content Fragment Models"),
        EDITABLE_TEMPLATES("Editable Templates"),
        GRAPHQL_PERSISTENT_QUERIES("GraphQL Persistent Queries");

        private final String value;

        ConfigCapabilities(final String val) {
            this.value = val;
        }

        public String value() {
            return value;
        }
    }
}
