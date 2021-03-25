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

package com.adobe.cq.testing.selenium.pagewidgets.sidepanel;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$$;

public class ComponentInspectorPanel extends BaseComponent {

    public ComponentInspectorPanel() {
        super("coral-panel .sidepanel-tab-componentinspector");
    }

    public ComponentInspectorItem getComponentInspectorItem(String path) {
        return new ComponentInspectorItem(path);
    }

    public class ComponentInspectorItem extends BaseComponent {

        private static final String COMPONENT_ITEM = "#ComponentInspector [data-path='%s'] [role='presentation']";

        public ComponentInspectorItem(String path) {
            super($$(String.format(COMPONENT_ITEM, path)).first());
            element().shouldBe(Condition.exist);
        }

        public boolean isSelected() {
            return element().getAttribute("selected") != null;
        }

        public boolean isOpen() {
            return element().has(Condition.cssClass("is-open"));
        }

        public ComponentInspectorItem select() {
            clickableClick(element().find("button"));
            return this;
        }

        public ComponentInspectorItem open() {
            if (!this.isOpen()) {
                select();
            }
            return this;
        }

        public ComponentInspectorItem close() {
            if (this.isOpen()) {
                select();
            }
            return this;
        }
    }

}
