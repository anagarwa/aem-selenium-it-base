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

package com.adobe.cq.testing.selenium.pagewidgets.granite;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * Helper class for a form field holding paths.
 */
public final class PathField extends BaseComponent {

    /**
     * @param selector The full CSS selector that leads to the HTML element.
     */
    public PathField(final String selector) {
        super(selector);
    }

    /**
     * Determines if this pathfield is configured to support input of multiple values.
     * @return true if this field supports multiples values or false otherwise
     */
    public boolean isMultiple() {
        return element().getAttribute("multiple") != null;
    }

    /**
     * Returns all the values associated with this pathfield.
     * @return empty array if there is no value or an array of the string values associated
     * with this pathfield
     */
    public String[] getValues() {
        final String values = element().$("coral-taglist coral-tag").getAttribute("value");
        return values.split(" ");
    }

    /**
     * Set a new path value into the input box of the pathfield picker.
     * @param value - The new path value
     */
    public void setValue(final String value) {
        element().setValue(value);
    }

    /**
     * Clicks on the "Open Selection Dialog" associated with this picker
     * and waits for the dialog to be visible on the screen.
     * @return the picker overlay component
     */
    public Picker openPicker() {
        final String cssSel = String.format("%s .foundation-autocomplete-inputgroupwrapper button[is='coral-button']", getCssSelector());
        clickableClick($(cssSel));

        Picker picker = new Picker("coral-dialog.foundation-picker-collection[open]");
        picker.waitVisible();

        return picker;
    }

    /**
     * Close the pathpicker.
     */
    public void closePicker() {
        Picker picker = new Picker("coral-dialog.foundation-picker-collection[open]");
        picker.close();
    }
}
