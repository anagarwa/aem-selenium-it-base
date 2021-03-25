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

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ComponentsPanel extends BaseComponent {

    private static final String COMPONENT_LIST = "coral-list-item-content>div";
    private static final String COMPONENT_ITEM = "coral-list-item div[data-path='%s']";
    private static final String SEARCH_FIELD = "input[data-editor-searchfilter-search]";
    private static final CoralSelect GROUP_FILTER = new CoralSelect("name='componentfilter_group_selector'");

    public ComponentsPanel() {
        super("coral-panel .editor-ComponentBrowser");
    }

    public void dropOnPage(final String componentResource, final String pageEditableResource) {
        final SelenideElement componentCard = $("[data-path='" + componentResource + "']");
        final SelenideElement dropTarget = $("[data-type='Editable'][data-path='" + pageEditableResource + "']");
        Helpers.dragOnPage(componentCard, dropTarget);
    }

    public SelenideElement getComponent(String resourceType) {
        SelenideElement component = element().find(String.format(COMPONENT_ITEM, resourceType));
        component.shouldBe(Condition.visible);
        return component;
    }

    public ElementsCollection getComponentList() {
        return element().findAll(COMPONENT_LIST);
    }

    public ElementsCollection search(String searchText) {
        int initialSize = getComponentList().size();
        element().findAll(SEARCH_FIELD).find(Condition.visible).setValue(searchText).pressEnter();
        Helpers.waitForListSizeChange(initialSize, getComponentList(), 1000);
        return getComponentList();
    }

    public ElementsCollection selectGroup(final String group) {
        int initialSize = getComponentList().size();
        GROUP_FILTER.click();
        GROUP_FILTER.selectList().selectByLabel(group);
        Helpers.waitForListSizeChange(initialSize, getComponentList(), 1000);
        return getComponentList();
    }

    /**
     * Scroll to the end of the component list.
     */
    public void scrollToEnd() {
        final ElementsCollection elements = getComponentList();
        int size = elements.size();
        if (size > 0) {
            SelenideElement element = elements.get(size - 1);
            element.scrollIntoView("{behavior: \"smooth\", block: \"end\", inline: \"end\"}");
            Helpers.waitForElementAnimationFinished(element);
        }
    }

    /**
     * Scroll to the top of the component list.
     */
    public void scrollToTop() {
        final ElementsCollection elements = getComponentList();
        int size = elements.size();
        if (size > 0) {
            SelenideElement element = elements.get(0);
            element.scrollIntoView("{behavior: \"smooth\", block: \"end\", inline: \"end\"}");
            Helpers.waitForElementAnimationFinished(element);
        }
    }

}