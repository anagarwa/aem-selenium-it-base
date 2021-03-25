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

import com.adobe.cq.testing.selenium.pageobject.granite.ViewType;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickUntil;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.Wait;

public class CFModelEditor extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(CFModelEditor.class);

    private static final String CF_MODEL_EDITOR_PATH = "/mnt/overlay/dam/cfm/models/editor/content/editor.html";

    private SelenideElement formFields = $("#form-fields");
    private SelenideElement dataTypesTab = $("#tab-add");

    @Override
    protected <T extends Wizard> T open(String path) {
        return super.open(CF_MODEL_EDITOR_PATH + path);
    }

    @Override
    public void next() {
        ExpectNav.on(super::next);
    }

    public void switchToDataTypes() {
        clickableClick(dataTypesTab);
    }

    /**
     * Drags and drops a data type template from the list to the form fields and assigns the correct ID to it.
     *
     * @param dataType - the FormFieldTemplate that should be dragged.
     * @return - the template with the correct ID
     */
    private <T extends CFModelDataType> T dragAndDrop(T dataType) {

        SelenideElement template = dataType.getDragabbleElement();
        switchToDataTypes();
        template.dragAndDropTo(formFields);
        SelenideElement newElement = $(".field-properties.ui-selected.field-properties-selected");
        String ID = newElement.attr("data-id");
        dataType.setID(ID);
        dataType.setViewElement(newElement);

        return dataType;
    }

    public <T extends CFModelDataType> T addDataType(final Class<T> dataTypeClass) {
        T dataType = getDataType(dataTypeClass);
        return dragAndDrop(dataType);
    }

    public <T extends CFModelDataType> T getDataType(final Class<T> dataTypeClass, String propertyName) {
        T dataType = getDataType(dataTypeClass);
        dataType.setIdByPropertyName(propertyName);
        return dataType;
    }

    public <T extends CFModelDataType> T getDataType(final Class<T> dataTypeClass) {
        T dataType = null;
        try {
            dataType = dataTypeClass.getConstructor(new Class[] { CFModelEditor.class } ).newInstance(this);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOG.error("Cannot instantiate {} due to error", dataTypeClass, e);
        }
        if (dataType == null) {
            throw new IllegalStateException("Could not get a valid model for " + dataTypeClass);
        }
        return dataType;
    }

    public abstract class CFModelDataType {
        protected SelenideElement propertyField = null;
        protected SelenideElement viewElement = null;
        protected SelenideElement tabProperties = null;
        protected SelenideElement field = null;
        protected SelenideElement fieldPropertiesView = null;
        protected SelenideElement fieldNameField = null;
        protected SelenideElement fieldNameMultiLineField = null;
        protected String ID;

        public abstract void updateSpecificPropertyElements();

        public void setPropertyName(String propertyName) {
            if (propertyField == null) {
                return;
            }
            propertyField.val(propertyName);
        }

        public SelenideElement getPropertyNameElement() {
            return propertyField != null ? propertyField : null;
        }

        public SelenideElement getDragabbleElement() {
            return $(".field.ui-draggable[data-fieldtype=\"" + getResourceType() + "\"]");
        }

        public abstract String getResourceType();

        public void setFieldName(String fieldName) {
            if (fieldNameField == null) {
                return;
            }
            fieldNameField.val(fieldName);
        }

        public void setMultiLineFieldName(String fieldName) {
            if (fieldNameMultiLineField == null) {
                return;
            }
            fieldNameMultiLineField.sendKeys(fieldName);
        }

        public void setIdByPropertyName(String name) {
            String selector = ".field-json-editor[name=" + name + "]";
            SelenideElement el = $(selector);
            Wait().until(webdriver -> el.shouldBe(Condition.visible));
            SelenideElement parent = el.closest("li");
            String dataId = parent.data("id");
            setID(dataId);
        }

        public void setID(String id) {
            ID = id;
            field = $("#form-builder-view li[data-id=\"" + ID + "\"]");
            tabProperties = $(".field-properties[data-id=\"" + ID + "\"]");
            propertyField = $("input.field-propertyName-descriptor[name=\"./content/items/" + ID + "/name\"]");
            fieldNameField = $("input.field-label-descriptor[name=\"./content/items/" + ID + "/fieldLabel\"]");
            fieldNameMultiLineField = $("input.field-label-descriptor[name=\"./content/items/" + ID + "/cfm-element\"]");
            updateSpecificPropertyElements();
        }

        public void setViewElement(SelenideElement viewElement) {
            this.viewElement = viewElement;
        }

        public void selectViewElement() {
            clickableClick(viewElement);
            String selector = ".field-properties[data-id=" + ID + "]";
            fieldPropertiesView = $(selector);
            Wait().until(webdriver -> fieldPropertiesView.shouldBe(Condition.visible));
        }

        public void selectFieldElement() {
            if (field == null) {
                return;
            }
            clickableClick(field);
            Wait().until(webdriver -> tabProperties.shouldBe(Condition.visible));
        }
    }

    public class SingleLineText extends CFModelDataType {

        private static final String RESOURCE_TYPE = "granite/ui/components/coral/foundation/form/textfield";

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

        }
    }

    public class MultiLineText extends CFModelDataType {

        private static final String RESOURCE_TYPE = "dam/cfm/admin/components/authoring/contenteditor/multieditor";
        AutoCompleteField<Picker> rteModelReference = null;
        CoralCheckbox allowNew = null;
        CoralCheckbox required = null;

        public CoralSelect getDefaultType() {
            return defaultType;
        }

        CoralSelect defaultType = null;

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {
            final String modelTypeSelector = String.format("css:.rte-modelPicker-descriptor[name=\"./content/items/%s/fragmentmodelreference\"]", this.ID);

            rteModelReference = new AutoCompleteField<>(modelTypeSelector, () -> new Picker("coral-dialog"), false);
            defaultType = new CoralSelect(String.format("name=\"./content/items/%s/default-mime-type\"", this.ID));
            allowNew = new CoralCheckbox(String.format("[name=\"./content/items/%s/checked\"]", this.ID));
            required = new CoralCheckbox(String.format("[name=\"./content/items/%s/required\"]", this.ID));
        }
        public Picker launchModelReferencePicker() throws TimeoutException {
            if (rteModelReference == null) {
                throw new UnsupportedOperationException("ID is not set for Fragment Reference object.");
            }
            rteModelReference.element().scrollIntoView(true);
            rteModelReference.element().shouldBe(Condition.visible);
            Picker modelFragmentPicker =
                    new Picker(
                            "[data-granite-pickerdialog-collection=\"#fragment-model-reference-picker-collection\"]",
                            ViewType.CARD);

            clickUntil(rteModelReference.element().$(".foundation-autocomplete-inputgroupwrapper button"),
                    modelFragmentPicker.element(), Condition.exist, 10, 250);

            modelFragmentPicker.waitVisible();

            return modelFragmentPicker;
        }

        public void useRichText() {
            getDefaultType().selectItemByValue("text/html");
        }

        public void usePlainText() {
            getDefaultType().selectItemByValue("text/plain");
        }

        public void useMarkdownText() {
            getDefaultType().selectItemByValue("text/x-markdown");
        }

        public void required() {
            if (required.isChecked()) {
                return;
            }
            required.click();
        }

        public void disallowRequired() {
            if (required.isChecked()) {
                required.click();
            }
        }

        public void allowFragmentCreation() {
            if (allowNew.isChecked()) {
                return;
            }
            allowNew.click();
        }

        public void disallowFragmentCreation() {
            if (allowNew.isChecked()) {
                allowNew.click();
            }
        }

    }

    public class Number extends CFModelDataType {

        private static final String RESOURCE_TYPE = "granite/ui/components/coral/foundation/form/numberfield";

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

        }
    }

    public class Boolean extends CFModelDataType {
        private static final String RESOURCE_TYPE = "granite/ui/components/coral/foundation/form/checkbox";

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

        }
    }

    public class DateAndTime extends CFModelDataType {

        private static final String RESOURCE_TYPE = "granite/ui/components/coral/foundation/form/datepicker";

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

        }
    }

    public class Enumeration extends CFModelDataType {

        private static final String RESOURCE_TYPE = "granite/ui/components/coral/foundation/form/select";

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

        }
    }

    public class Tags extends CFModelDataType {

        private static final String RESOURCE_TYPE = "cq/gui/components/coral/common/form/tagfield";

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

        }
    }

    public class ContentReference extends CFModelDataType {

        private static final String RESOURCE_TYPE = "dam/cfm/models/editor/components/contentreference";

        AutoCompleteField<Picker> rootPathElement = null;
        AutoCompleteField<Picker> contentReference = null;
        CoralSelect renderAs = null;

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

            final String rootPathSelector = String.format("css:[name=\"./content/items/%s/rootPath\"]", this.ID);
            final String defaultValueSelector = String.format("css:.field-default-descriptor[name=\"./content/items/%s/value\"]", this.ID);

            rootPathElement = new AutoCompleteField<>(rootPathSelector, getPickerCallable(rootPathSelector), false);
            contentReference = new AutoCompleteField<>(defaultValueSelector, getPickerCallable(defaultValueSelector), false);
            renderAs = new CoralSelect(String.format("name=\"./content/items/%s/sling:resourceType\"", this.ID));
        }

        private Callable<Picker> getPickerCallable(String selector) {
            return () -> {
                final String cssSel = String.format("%s .foundation-autocomplete-inputgroupwrapper button[is='coral-button']", selector);
                clickableClick($(cssSel));

                Picker picker = new Picker();
                picker.waitVisible();

                return picker;
            };
        }

        public void useMultiField() {
            getRenderAs().selectItemByValue("granite/ui/components/coral/foundation/form/multifield");
        }

        public Picker launchRootPathPicker() throws TimeoutException {
            if (rootPathElement == null) {
                throw new UnsupportedOperationException("ID is not set for Content Reference object.");
            }
            rootPathElement.element().scrollIntoView(true);
            rootPathElement.element().shouldBe(Condition.visible);
            Picker rootPathPicker =
                    new Picker(
                            "[data-granite-pickerdialog-collection=\"#granite-ui-pathfield-picker-collection\"]",
                            ViewType.COLUMN);

            clickUntil(rootPathElement.element().$(".foundation-autocomplete-inputgroupwrapper button"),
                    rootPathPicker.element(), Condition.exist, 10, 250);

            rootPathPicker.waitVisible();

            return rootPathPicker;
        }

        public Picker launchDefaultValuePicker() throws TimeoutException {
            if (contentReference == null) {
                throw new UnsupportedOperationException("ID is not set for Content Reference object.");
            }
            contentReference.element().scrollIntoView(true);
            contentReference.element().shouldBe(Condition.visible);
            Picker defaultPathPicker =
                    new Picker(
                            "[data-granite-pickerdialog-collection=\"#granite-ui-pathfield-picker-collection\"]",
                            ViewType.COLUMN);

            clickUntil(contentReference.element().$(".foundation-autocomplete-inputgroupwrapper button"),
                    defaultPathPicker.element(), Condition.exist, 10, 250);

            defaultPathPicker.waitVisible();

            return defaultPathPicker;
        }

        public CoralSelect getRenderAs() {
            return renderAs;
        }
    }

    public class FragmentReference extends CFModelDataType {
        private static final String RESOURCE_TYPE = "dam/cfm/models/editor/components/fragmentreference";

        AutoCompleteField<Picker> modelTypeElement = null;
        AutoCompleteField<Picker> rootPathElement = null;
        AutoCompleteField<Picker> fragmentReference = null;
        SelenideElement pickerSourceInput = null;
        CoralSelect renderAs = null;
        CoralCheckbox allowNew = null;

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

            final String modelTypeSelector = String.format("css:.field-fragmentModelReference-descriptor[name=\"./content/items/%s/fragmentmodelreference\"]", this.ID);
            final String rootTypeSelector = String.format("css:.field-fragmentReferenceRootPath-descriptor[name=\"./content/items/%s/rootPath\"]", this.ID);
            final String defaultValueSelector = String.format("css:.field-fragment-reference[name=\"./content/items/%s/value\"]", this.ID);
            final String pickerSourceInputSelector = String.format(".field-picker-src-descriptor[name=\"./content/items/%s/pickerSrc\"]", this.ID);

            modelTypeElement = new AutoCompleteField<>(modelTypeSelector, getPickerCallable(modelTypeSelector),false);
            rootPathElement = new AutoCompleteField<>(rootTypeSelector, getPickerCallable(rootTypeSelector), false);
            fragmentReference = new AutoCompleteField<>(defaultValueSelector, getPickerCallable(defaultValueSelector), false);
            pickerSourceInput = $(pickerSourceInputSelector);
            renderAs = new CoralSelect(String.format("name=\"./content/items/%s/sling:resourceType\"", this.ID));
            allowNew = new CoralCheckbox(String.format("[name=\"./content/items/%s/allowNew\"]", this.ID));
        }

        private Callable<Picker> getPickerCallable(String selector) {
            return () -> {
                final String cssSel = String.format("%s .foundation-autocomplete-inputgroupwrapper button[is='coral-button']", selector);
                clickableClick($(cssSel));

                Picker picker = new Picker();
                picker.waitVisible();

                return picker;
            };
        }

        public void useMultiField() {
            getRenderAs().selectItemByValue("dam/cfm/models/editor/components/fragmentreference/multifield");
        }

        /**
         * Sets model type to the specific path.
         *
         * @param modelPath the repository path where the model definition is stored
         */
        public void setModelType(String modelPath) {
            if (modelTypeElement == null) {
                throw new UnsupportedOperationException();
            }
            modelTypeElement.sendKeys(modelPath);
        }

        public AutoCompleteField<Picker> getModelTypeElement() {
            return modelTypeElement;
        }

        public Picker launchModelTypePicker() throws TimeoutException {
            if (modelTypeElement == null) {
                throw new UnsupportedOperationException("ID is not set for Fragment Reference object.");
            }
            modelTypeElement.element().scrollIntoView(true);
            modelTypeElement.element().shouldBe(Condition.visible);
            Picker modelFragmentPicker =
                    new Picker(
                            "[data-granite-pickerdialog-collection=\"#fragment-model-reference-picker-collection\"]",
                            ViewType.CARD);

            clickUntil(modelTypeElement.element().$(".foundation-autocomplete-inputgroupwrapper button"),
                    modelFragmentPicker.element(), Condition.exist, 10, 250);

            modelFragmentPicker.waitVisible();

            return modelFragmentPicker;
        }

        public Picker launchRootPathPicker() throws TimeoutException {
            if (rootPathElement == null) {
                throw new UnsupportedOperationException("ID is not set for Fragment Reference object.");
            }
            rootPathElement.element().scrollIntoView(true);
            rootPathElement.element().shouldBe(Condition.visible);
            Picker rootPathPicker =
                    new Picker(
                            "[data-granite-pickerdialog-collection=\"#granite-ui-pathfield-picker-collection\"]",
                            ViewType.COLUMN);

            clickUntil(rootPathElement.element().$(".foundation-autocomplete-inputgroupwrapper button"),
                    rootPathPicker.element(), Condition.exist, 10, 250);

            rootPathPicker.waitVisible();

            return rootPathPicker;
        }

        public Picker launchDefaultValuePicker() throws TimeoutException {
            if (fragmentReference == null) {
                throw new UnsupportedOperationException("ID is not set for Fragment Reference object.");
            }
            fragmentReference.element().scrollIntoView(true);
            fragmentReference.element().shouldBe(Condition.visible);
            Picker defaultPathPicker =
                    new Picker(
                            "[data-granite-pickerdialog-collection=\"#fragment-reference-picker-collection\"]",
                            ViewType.COLUMN);

            clickUntil(fragmentReference.element().$(".foundation-autocomplete-inputgroupwrapper button"),
                    defaultPathPicker.element(), Condition.exist, 10, 250);

            defaultPathPicker.waitVisible();

            return defaultPathPicker;
        }

        public SelenideElement getPickerSourceInput() {
            return pickerSourceInput;
        }

        public CoralSelect getRenderAs() {
            return renderAs;
        }

        public void allowFragmentCreation() {
            if (allowNew.isChecked()) {
                return;
            }
            allowNew.click();
        }

        public void disallowFragmentCreation() {
            if (allowNew.isChecked()) {
                allowNew.click();
            }
        }
    }

    public class JsonField extends CFModelDataType {
        private static final String RESOURCE_TYPE = "dam/cfm/admin/components/authoring/contenteditor/jsoneditor";

        public String getResourceType() {
            return RESOURCE_TYPE;
        }

        @Override
        public void updateSpecificPropertyElements() {

        }
    }

}
