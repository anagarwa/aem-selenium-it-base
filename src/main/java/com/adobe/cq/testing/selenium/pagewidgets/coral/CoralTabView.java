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

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.SelenideElement;

import java.util.stream.Stream;

public final class CoralTabView extends BaseComponent {

    /**
     * Simple constructor for a coral-tabview
     */
    public CoralTabView() {
        super("coral-tabview");
    }

    public CoralTabList tabList() {
        return new CoralTabList(element().$("coral-tablist"));
    }

    public class CoralTabList extends BaseComponent {

        private CoralTabList(SelenideElement element) {
            super(element);
        }

        public Stream<CoralTab> tabs() {
            return element().$$("coral-tab").stream().map(CoralTab::new);
        }

        public CoralTab getTabByIcon(String icon) {
            return tabs().filter((t) -> t.getIcon().equals(icon)).findFirst().orElseThrow();
        }

        public class CoralTab extends BaseComponent {

            private CoralTab(SelenideElement element) {
                super(element);
            }

            public String getIcon() {
                return element().attr("icon");
            }

            public String getTitle() {
                return element().attr("title");
            }

            public String getControls() {
                return element().attr("aria-controls");
            }
        }

    }
}
