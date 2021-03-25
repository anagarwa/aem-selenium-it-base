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

package com.adobe.cq.testing.selenium.pagewidgets.xf;

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralPopOver;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralList;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Masonry;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VariationsPanel extends BaseComponent {

    private static final SelenideElement CREATE_BUTTON = $("button[trackingelement=\"create\"]");

    public VariationsPanel() {
        super("coral-panel .sidepanel-tab-variations");
    }

    public ActionComponent<CreateVariationMenu> create() {
        return new ActionComponent<CreateVariationMenu>(CREATE_BUTTON, () -> new CreateVariationMenu(), false);
    }

    public CoralList list() {
        return new CoralList(".variations-list");
    }

    public class CreateVariationMenu extends BaseComponent {

        public CreateVariationMenu() {
            super(CoralPopOver.firstOpened().element());
        }

        public ActionComponent<CreateVariationDialog> variation() {
            return new ActionComponent<CreateVariationDialog>(
                    element().$("#AddVariation"),
                    () -> new CreateVariationDialog(), false);
        }

        public ActionComponent<CreateLiveCopyVariationDialog> variationAsLiveCopy() {
            return new ActionComponent<CreateLiveCopyVariationDialog>(
                    element().$("#AddLcVariation"),
                    () -> new CreateLiveCopyVariationDialog(), false);
        }

    }

    public class CreateVariationDialog extends Dialog {

        private static final String DIALOG_INPUT = "coral-dialog.is-open input";
        private static final String DIALOG_TEXT = "coral-dialog.is-open textarea";

        private final AutoCompleteField<TemplatePickerDialog> template = new AutoCompleteField(
                new FormField("template").getName(),
                () -> new TemplatePickerDialog(), false);

        private final SelenideElement name = new FormField("pageName").getFullyDecoratedElement(DIALOG_INPUT);
        private final SelenideElement description = FormField.DESCRIPTION.getFullyDecoratedElement(DIALOG_TEXT);
        private final SelenideElement title = new FormField("pageTitle").getFullyDecoratedElement(DIALOG_INPUT);
        private final AutoCompleteField tags = new AutoCompleteField("cq:tags");

        public AutoCompleteField<TemplatePickerDialog> template() {
            return template;
        }

        public SelenideElement title() {
            return title;
        }

        public SelenideElement name() {
            return name;
        }

        public SelenideElement description() {
            return description;
        }

        public AutoCompleteField tags() {
            return tags;
        }
    }

    public class CreateLiveCopyVariationDialog {
    }

    public class TemplatePickerDialog extends Dialog {

        private Collection collection = new Masonry("#granite-ui-templatefield-picker-collection");

        public TemplatePickerDialog() {
            super("[data-granite-pickerdialog-collection=\"#granite-ui-templatefield-picker-collection\"]");
        }

        public Collection collection() {
            return collection;
        }

        public TemplatePickerDialog selectTemplate(final String templateId) {
            collection.selectItem(templateId);
            return this;
        }

        public TemplatePickerDialog deselectTemplate(final String templateId) {
            collection.deselectItem(templateId);
            return this;
        }
    }
}