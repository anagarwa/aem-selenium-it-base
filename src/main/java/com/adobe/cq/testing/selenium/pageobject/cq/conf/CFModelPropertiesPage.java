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

import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.codeborne.selenide.SelenideElement;

public class CFModelPropertiesPage extends BasePage {
    
    private static final String CF_MODEL_PROPERTIES_PAGE_URL = "/mnt/overlay/dam/cfm/models/console/content/properties.html";
    private static final SelenideElement TITLE_FIELD = new FormField("./jcr:content/jcr:title").getFullyDecoratedElement("input");
    private static final SelenideElement DESCRIPTION_FIELD = new FormField("./jcr:content/jcr:description").getFullyDecoratedElement("textarea");
    private final AutoCompleteField<Picker> tags = new AutoCompleteField<Picker>("./jcr:content/cq:tags", () -> new Picker("coral-dialog"), false);
    
    public CFModelPropertiesPage(String... pagePath) {
        super(pagePath[0]);
    }
    
    public CFModelPropertiesPage() {
        super(CF_MODEL_PROPERTIES_PAGE_URL);
    }
    
    public SelenideElement title() {
        return TITLE_FIELD;
    }

    public SelenideElement description() {
        return DESCRIPTION_FIELD;
    }

    public AutoCompleteField<Picker> tags() {
        return tags;
    }
}
