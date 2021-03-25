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
import com.adobe.cq.testing.selenium.pageobject.granite.ViewType;
import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.*;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.adobe.cq.testing.selenium.pagewidgets.sidepanel.CFEditorSidePanel;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Content Fragment Editor page class.
 */
public final class ContentFragmentEditorPage extends BasePage {

    private static final String CF_EDITOR_BASE_URL = "/editor.html";
    private final CoralActionBar coralActionBar = new CoralActionBar();
    private final CoralAlert topAlert = new CoralAlert(".content-fragment-statusbar");
    private final SelenideElement cfmFormElement = $("#aem-cfm-editor-elements-form .cfm-Form");

    public ContentFragmentEditorPage() {
        super(CF_EDITOR_BASE_URL);
    }

    @SuppressWarnings({"unchecked"})
    public ContentFragmentEditorPage open(@Nonnull final String path) {
        return super.open(CF_EDITOR_BASE_URL + path);
    }

    public CFEditorSidePanel sidePanel() {
        return new CFEditorSidePanel();
    }

    public VariationsPanel variations() {
        return new VariationsPanel();
    }

    public CoralAlert statusAlert() {
        return topAlert;
    }

    public String getModelEditorLink() {
        return coralActionBar.element().$(".cfm-editor-title-model a").getAttribute("href");
    }

    /**
     * Returns an instance of a basic component that matches a Content Fragment element.
     *
     * This method works for BaseComponents that have simple constructors, but not more complex ActionComponent or
     * AutoCompleteField.
     *
     * @param elementName - the name of the element to be found
     * @param componentType - the class of the component that needs to be instantiated
     * @return - an instance of componentType that matches the componentType
     * @throws IllegalArgumentException if it cannot find the element
     */
    public <T extends BaseComponent> T getElement(final String elementName, Class<T> componentType) throws IllegalArgumentException {
        SelenideElement foundElement = cfmFormElement.$(getElementSelector(elementName)).should(Condition.exist);
        /* TODO : find a better polling method to make sure the element / ui is ready */
        sleep(1000);
        T componentInstance;
        try {
            componentInstance = componentType.getConstructor(SelenideElement.class).newInstance(foundElement.parent());
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot return the given type for element " + elementName, e);
        }
        return componentInstance;
    }

    public String getElementSelector(final String elementName) {
        return String.format("[data-element=\"%s\"]", elementName);
    }

    public ActionComponent<AssetsPage> cancel() {
        SelenideElement cancelBtn = coralActionBar.element().$("button.button-cancel");
        return new ActionComponent<>(cancelBtn, AssetsPage::new, true);
    }

    public void activate() {
        SelenideElement activateBtn = coralActionBar.element().$("button.button-publish");
        activateBtn.click();
        SelenideElement publishBtn = $("button.foundation-wizard-control");
        publishBtn.click();
    }

    public SelenideElement status() {
        SelenideElement status = coralActionBar.element().$(".cf-status");
        return status;
    }

    public ActionComponent<AssetsPage> save() {
        SelenideElement saveBtn = coralActionBar.element().$("button.button-apply");
        return new ActionComponent<>(saveBtn, AssetsPage::new, true);
    }

    @Override
    public <T extends BasePage> T open() {
        T open = super.open();
        // need extra wait as content fragment editor is really heavy to load (on sprout mainly)
        Helpers.waitDOMIdled(1000);
        return open;
    }

    public static class VariationsPanel extends BaseComponent {

        public VariationsPanel() {
            super("coral-panel.is-selected");
        }

        public ActionComponent<CreateRenameVariationDialog> create() {
            SelenideElement btnCreateVariation = element().$("button.create-variation");
            return new ActionComponent<>(btnCreateVariation, CreateRenameVariationDialog::new, false);
        }

        public Stream<Item> listVariations() {
            return element().$$(".content-fragment-variations li").stream().map(Item::new);
        }

        public static class Item extends BaseComponent {

            private static final CoralSelect CORAL_SELECT = new CoralSelect("class=\"variations-quick-actions\"");

            public Item(SelenideElement element) {
                super(element);
            }

            public String name() {
                return element().data("variation-name");
            }

            public String title() {
                return element().data("variation-title");
            }

            public String description() {
                return element().$(".variation-description").getText();
            }

            public boolean isActive() {
                return element().has(Condition.cssClass("is-active"));
            }

            public boolean isMain() {
                return element().has(Condition.cssClass("main-variation"));
            }

            public ActionComponent<CreateRenameVariationDialog> rename() {
                return new ActionComponent<>(CORAL_SELECT.element(), () -> {
                    CORAL_SELECT.selectList().getItemByValue("js-action-rename");
                    return new CreateRenameVariationDialog();
                }, false);
            }

            public ActionComponent<DeleteDialog> delete() {
                return new ActionComponent<>(CORAL_SELECT.element(), () -> {
                    CORAL_SELECT.selectList().getItemByValue("js-action-delete");
                    return new DeleteDialog();
                }, false);
            }

            public ContentFragmentEditorPage select() {
                if (!isActive()) {
                    ExpectNav.on(this::click);
                }
                return new ContentFragmentEditorPage();
            }
        }
    }

    public static class DeleteDialog extends Dialog {

        public ContentFragmentEditorPage delete() {
            ExpectNav.on(this::clickWarning);
            return new ContentFragmentEditorPage();
        }

        public DeleteDialog cancel() {
            clickDefault();
            return this;
        }

    }

    public static class SingleTextElement extends BaseComponent {

        public SingleTextElement(SelenideElement element) {
            super(element);
        }

        public void setText(final String text) {
            getText().setValue(text);
        }

        public SelenideElement getText() {
            return element().$("input[data-element]");
        }
    }

    /**
     * Common methods and properties for Content and Fragment References
     */
    public static class ReferenceElement extends BaseComponent {
        public ReferenceElement(SelenideElement element) {
            super(element);
        }

        AutoCompleteField<Picker> pathField = new AutoCompleteField<>(element(), () -> new Picker().setViewType(ViewType.COLUMN), false);

        public AutoCompleteField getPathField() {
            return pathField;
        }

        public String getValue() {
            return pathField.value();
        }

        /**
         * Selects a new reference path on this element using picker.
         *
         * @param damRootPath - the path under dam that should be visible when selecting the item
         * @param referencePath - the reference path that should be selected
         */
        public void pickReference(String damRootPath, String referencePath) {
            Picker referencePicker = picker();
            referencePicker.waitVisible();
            referencePicker.activateItem(damRootPath);
            referencePicker.collection().selectItem(referencePath);
            referencePicker.submit();
        }

        public Picker picker() {
            return pathField.perform();
        }
    }

    public static class ContentReferenceElement extends ReferenceElement {

        public ContentReferenceElement(SelenideElement element) {
            super(element);
        }

        /**
         * Alias for ReferenceElement#pickReference, to avoid breaking existing usage.
         */
        public void pickContentReference(String damRootPath, String referencePath) {
            pickReference(damRootPath, referencePath);
        }
    }

    public static class MultiContentReferencesElement extends BaseComponent {

        public MultiContentReferencesElement(SelenideElement element) {
            super(element);
        }

        /**
         * This method selects different Content Fragments in the multifield element given as a parameter.
         *
         * @param damRootPath the root path to activate
         * @param referencePaths all the resources to select
         */
        public void selectContentReferences(String damRootPath, String... referencePaths) {

            // first check if the multifield element is empty
            ElementsCollection collection = element().findAll("coral-multifield-item");
            if (!collection.isEmpty()) {
                throw new IllegalArgumentException("The linked multifield is not empty.");
            }

            // add all the fragment references by clicking the add button
            for (int i = 0; i < referencePaths.length; i++) {
                clickableClick(element().$("button[coral-multifield-add]"));
            }

            // get the collection again and check if the number of coral-multifield-items is the same as the referencePaths length
            collection = element().findAll("coral-multifield-item");
            if (collection.size() != referencePaths.length) {
                throw new IllegalArgumentException("There was issue with adding the multifield items to the multifield.");
            }

            List<BaseComponent> contentReferenceElements = collection.stream()
                    .map((multifieldItem) -> new BaseComponent(multifieldItem.$(".field-content-reference")))
                    .collect(Collectors.toList());
            Iterator<BaseComponent> iterator = contentReferenceElements.iterator();
            for (String path : referencePaths) {
                iterator.next().adaptTo(ContentReferenceElement.class).pickContentReference(damRootPath, path);
            }
        }
    }

    public static class FragmentReferenceElement extends ReferenceElement {

        public FragmentReferenceElement(SelenideElement element) {
            super(element);
        }

        /**
         * Alias for ReferenceElement#pickReference, to avoid breaking existing usage.
         */
        public void pickFragmentReference(String damRootPath, String referencePath) {
            pickReference(damRootPath, referencePath);
        }

        public ActionComponent<AddNewFragmentWizard> addNewFragment() {
            SelenideElement parentDiv = element().closest("div");
            SelenideElement anchorLink = parentDiv.$(".new-content-fragment");
            return new ActionComponent<>(anchorLink, AddNewFragmentWizard::new, false);
        }

        public SelenideElement getEditLink() {
            final String fieldName = element().$("[data-element]").attr("data-element");
            final SelenideElement parentDiv = element().closest("div");
            final String selector = ".new-content-fragment[data-new-element=\""+fieldName+"\"]";
            final SelenideElement anchorLink = parentDiv.$(selector);
            return anchorLink;
        }

        public SelenideElement getCreateLink() {
            final String fieldName = element().$("[data-element]").attr("data-element");
            final SelenideElement parentDiv = element().closest("div");
            final String selector = ".edit-content-fragment[data-edit-element=\""+fieldName+"\"]";
            final SelenideElement anchorLink = parentDiv.$(selector);
            return anchorLink;
        }
    }

    public static class MultiFragmentReferencesElement extends BaseComponent {

        public MultiFragmentReferencesElement(SelenideElement element) {
            super(element);
        }

        /**
         * This method selects different Content Fragments in the multifield element given as a parameter.
         *
         * @param damRootPath the root path to activate
         * @param referencePaths all the resources to select
         */
        public void selectFragmentReferences(String damRootPath, String... referencePaths) {

            // first check if the multifield element is empty
            ElementsCollection collection = element().findAll("coral-multifield-item");
            if (!collection.isEmpty()) {
                throw new IllegalArgumentException("The linked multifield is not empty.");
            }

            // add all the fragment references by clicking the add button
            for (int i = 0; i < referencePaths.length; i++) {
                clickableClick(element().$("button[coral-multifield-add]"));
            }

            // get the collection again and check if the number of coral-multifield-items is the same as the referencePaths length
            collection = element().findAll("coral-multifield-item");
            if (collection.size() != referencePaths.length) {
                throw new IllegalArgumentException("There was issue with adding the multifield items to the multifield.");
            }

            List<BaseComponent> fragmentReferenceElements = collection.stream()
                    .map((multifieldItem) -> new BaseComponent(multifieldItem.$(".field-fragment-reference")))
                    .collect(Collectors.toList());
            Iterator<BaseComponent> iterator = fragmentReferenceElements.iterator();
            for (String path : referencePaths) {
                iterator.next().adaptTo(FragmentReferenceElement.class).pickFragmentReference(damRootPath, path);
            }
        }

        private ElementsCollection getFields() {
            return element().$$(".field-fragment-reference");
        }

        public int size() {
            return getFields().size();
        }

        public SelenideElement getField(int index) {
            return getFields().get(index);
        }

        public FragmentReferenceElement getFragmentReference(int index) {
            final SelenideElement elem = getField(index);
            return new MultiFragmentReferencesElement.MultifieldItemFragmentReference(elem);
        }

        public SelenideElement getAddButton() {
            return element().$("button[coral-multifield-add]");
        }

        public SelenideElement getCreateLink() {
            final String fieldName = element().$("[data-element]").attr("data-element");
            final String xpath = "following-sibling::a[@data-new-element=\""+fieldName+"\"]";
            return element().$x(xpath);
        }

        /* Override the FragmentReference to handle the slightly different structure of a multifield item */
        private static class MultifieldItemFragmentReference extends FragmentReferenceElement {

            public MultifieldItemFragmentReference(SelenideElement element) {
                super(element);
            }

            public SelenideElement getEditLink() {
                final String fieldName = element().attr("name");
                final String xpath = "following-sibling::a[@data-edit-element=\""+fieldName+"\"]";
                return element().$x(xpath);
            }
        }
    }

    public static class NumberElement extends SingleTextElement {

        public NumberElement(SelenideElement element) {
            super(element);
        }

        @Override
        public SelenideElement getText() {
            return element().$("coral-numberinput[data-element] input");
        }
    }

    public static class RichTextElement extends BaseComponent {

        public RichTextElement(SelenideElement element) {
            super(element);
        }

        public ActionComponent<RichTextDialog> enterFullscreen() {
            SelenideElement fullscreenIcon = new CoralIcon(element(), "fullScreen").element();
            return new ActionComponent<>(fullscreenIcon, RichTextDialog::new, false);
        }

        public void addDocumentFragment(String cfPath) {
            addDocumentFragment(cfPath, true);
        }

        public void addDocumentFragment(String cfPath, boolean useSelection) {
            if(useSelection) {
                super.element().find("p").doubleClick();
            }else{
                super.element().find("p").click();
            }

            SelenideElement icon = new CoralIcon(element(), "documentFragment").element();
            ActionComponent actionComponent = new ActionComponent<>(icon, RichTextDialog::new, false);
            actionComponent.click();

            // The iframe of the assetpicker (the URL looks like /aem/assetpicker?mode=single&fragmentType=/conf/.*/settings/dam/cfm/models/.*)
            SelenideElement iframe = $("iframe[src*=\"/aem/assetpicker?mode=single&fragmentType\"]");
            Selenide.switchTo().frame(iframe);

            // Using the search to get the content fragment
            SelenideElement searchCFM=$(".foundation-layout-panel-header .granite-pickerdialog-searchfield input");
            searchCFM.setValue(cfPath);
            searchCFM.pressEnter();

            String modelSelector = "[data-granite-collection-item-id=\"" + cfPath + "\"]";
            SelenideElement selenideElement= $(modelSelector);
            selenideElement.click(); // after this click it's selected

            SelenideElement done= $(".asset-picker-done");
            done.click();
        }

        public void setText(final String text) {
            SelenideElement editableElement = getText();
            editableElement.toWebElement().clear();
            editableElement.sendKeys(text);
        }

        public void setPlainTextarea(final String text) {
            SelenideElement editableElement = getPlainTextarea();
            editableElement.toWebElement().clear();
            editableElement.sendKeys(text);
        }

        public void setMarkdownTextarea(final String text) {
            SelenideElement editableElement = getMarkdownTextarea();
            editableElement.toWebElement().clear();
            editableElement.sendKeys(text);
        }

        public SelenideElement getText() {
            return element().$("div[contenteditable=\"true\"]");
        }

        public SelenideElement getPlainTextarea() {
            return element().$(".cfm-multieditor .plaintext textarea");
        }

        public SelenideElement getMarkdownTextarea() {
            return element().$(".cfm-multieditor .markdown textarea");
        }

        public SelenideElement getTextCode() {
            return element().$("div[data-form-view-container=\"true\"] div[data-cfm-richtext-editable=\"true\"] p");
        }


    }

    public static class RichTextDialog extends Dialog {

        public void exitFullscreen() {
            new CoralIcon(element(), "fullScreenExit").click();
        }

        public void setText(final String text) {
            SelenideElement editableElement = getText();
            editableElement.toWebElement().clear();
            editableElement.sendKeys(text);
        }

        public SelenideElement getText() {
            return element().$("div[contenteditable=\"true\"]");
        }

    }

    public static class CreateRenameVariationDialog extends Dialog {

        private static final SelenideElement TITLE_FIELD = new FormField("title").getFullyDecoratedElement("input");
        private static final SelenideElement DESCRIPTION_FIELD = new FormField("description").getFullyDecoratedElement("textarea");

        public SelenideElement title() {
            return element().$(TITLE_FIELD.getSearchCriteria());
        }

        public SelenideElement description() {
            return element().$(DESCRIPTION_FIELD.getSearchCriteria());
        }

        public ContentFragmentEditorPage rename() {
            return add();
        }

        public ContentFragmentEditorPage add() {
            ExpectNav.on(this::clickPrimary);
            return new ContentFragmentEditorPage();
        }

        public CreateRenameVariationDialog cancel() {
            clickDefault();
            return this;
        }
    }
}
