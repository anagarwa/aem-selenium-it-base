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

package com.adobe.cq.testing.selenium.pagewidgets.cq.conf;

import com.adobe.cq.testing.selenium.pageobject.cq.conf.CFModelCreateWizard;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.utils.ExpectNav;

public class CFModelCreateAction extends BaseComponent {

    public static final String CF_MODEL_CREATE_SELECTOR = "button.cq-cfm-models-createmodel-activator";

    public CFModelCreateAction() {
        super(CF_MODEL_CREATE_SELECTOR);
    }

    public CFModelCreateWizard perform() {
        ExpectNav.on(this::click);
        return new CFModelCreateWizard();
    }

}
