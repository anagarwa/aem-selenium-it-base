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

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public final class CoralShell {

    private SelenideElement menuElement = $("coral-shell-menu");

    private SelenideElement homeElement = $("coral-shell-header-home");

    private SelenideElement navigationElement = $("coral-tab[icon='compass']");

    private SelenideElement hammerElement = $("coral-tab[icon='hammer']");

    public CoralShell toggleMenu() {
        homeElement.click();
        return this;
    }

    public void openNavigation() {
        if (!menuElement.has(Condition.visible)) {
            toggleMenu();
        }
        menuElement.shouldBe(Condition.visible);
        navigationElement.click();
    }

    public void openTools() {
        if (!menuElement.has(Condition.visible)) {
            toggleMenu();
        }
        menuElement.shouldBe(Condition.visible);
        hammerElement.click();
    }
}
