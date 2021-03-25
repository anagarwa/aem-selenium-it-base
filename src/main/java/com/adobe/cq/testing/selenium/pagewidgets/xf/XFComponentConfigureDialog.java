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

import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.codeborne.selenide.SelenideElement;

public class XFComponentConfigureDialog extends Dialog {

    private final AutoCompleteField<Picker> variationField = new AutoCompleteField(
            new FormField("./fragmentPath").getName(),
            () -> new Picker("coral-dialog.foundation-picker-collection.is-open"), false);

    public XFComponentConfigureDialog(final SelenideElement element) {
        super(element);
    }

    public AutoCompleteField<Picker> getVariation() {
        return variationField;
    }

}
