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
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class CoralTree extends BaseComponent {

    private static final String TREE_ITEM_ELEMENT = "coral-tree-item";

    /**
     * Construct the coral-tree object.
     */
    public CoralTree() { super("coral-tree"); }

    /**
     * @return all the tree items in this tree.
     */
    public ElementsCollection items() {
        return element().$$(TREE_ITEM_ELEMENT);
    }

    /**
     * @return the tree item that is selected in this tree.
     */
    public SelenideElement getSelectedItem() {
        return element().$(String.format("%s[selected]", TREE_ITEM_ELEMENT));
    }

    /**
     * @return the content element for this tree.
     */
    public SelenideElement content() {
        return element().$("coral-tree-content");
    }

}
