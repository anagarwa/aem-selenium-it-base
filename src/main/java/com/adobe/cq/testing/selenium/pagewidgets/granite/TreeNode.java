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

import com.adobe.cq.testing.selenium.Constants;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.Wait;

public class TreeNode extends BaseComponent {

    private final boolean isRoot;
    private final String chevronSelector;
    private final String childrenContainerSelector;
    private final String waitingIndicatorSelector;
    private final String selectedNodeSelector;

    /**
     * @param nodeSelector the selector to wrap on.
     * @param isChild true if this is a child node.
     */
    public TreeNode(final String nodeSelector, final boolean isChild) {
        super(nodeSelector);
        isRoot = !isChild;
        if (isChild) {
            childrenContainerSelector = nodeSelector + " + div > div:first-of-type";
        } else {
            childrenContainerSelector = nodeSelector + " > div:first-of-type";
        }
        chevronSelector = String.format("%s > div:first-child > coral-icon", getCssSelector());
        waitingIndicatorSelector = String.format("%s > div > div > div > coral-wait", chevronSelector);
        selectedNodeSelector = String.format("%s[class*='fnd-isSelected--']", getCssSelector());
    }

    private String getSelectorForChild(final String childName) {
        return String.format("%s > div[name=\"%s\"]", childrenContainerSelector, childName);
    }

    /**
     * Returns the selector for the selected node.
     *
     * @return the selected node selector
     */
    private String selectorForSelectedNode() {
        return String.format("%s div[class*='fnd-isSelected--']", selectorForContent());
    }

    /**
     * Returns the selector for a child node at a certain index.
     *
     * @param   index - the 0 based index we are looking for
     * @return the selector for the child node
     */
    private String getSelectorForChildNodeAtIndex(final int index) {
        // We will have one fnd-TreeItem element then one fnd-TreeItem-content element so we need to skip those
        final String selector = "%s > div%s[class*='fnd-TreeItem--']:nth-child(%d)";
        if (element().$("div[class*=fnd-Tree-root]").exists()) {
            return String.format(selector, selectorForContent(), "", index * 2 + 1);
        } else {
            return String.format(selector, selectorForContent(), " > div", index * 2 + 1);
        }
    }

    /**
     * Returns the selector for the content of a node.
     *
     * @return The selector for the content of the node
     */
    private String selectorForContent() {
        if (element().$("div[class*=fnd-Tree-root]").exists()) {
            return String.format("%s div[class*=fnd-Tree-root]", getCssSelector());
        } else {
            return String.format("%s ~ div[class*='fnd-TreeItem-content']", getCssSelector());
        }
    }

    /**
     * @return true if this node is expanded.
     */
    public boolean isExpanded() {
        if (isRoot) {
            return true;
        }

        if (!$(chevronSelector).exists()) {
            return true;
        }

        $(waitingIndicatorSelector).should(Condition.exist);

        return $(childrenContainerSelector).is(Condition.visible);
    }

    /**
     * Check for child name existence.
     * @param childName to look for.
     * @return true if it has such child name.
     */
    public boolean hasChildWithName(final String childName) {
        if (!isExpanded()) {
            return false;
        }

        return $(getSelectorForChild(childName)).exists();
    }

    /**
     * @param childName to look for.
     * @return the child TreeNodeComponent, null if that child doesn't exists.
     */
    public TreeNode getChildWithName(final String childName) {
        if (!hasChildWithName(childName)) {
            return null;
        }

        final String childSelector = getSelectorForChild(childName);

        $(childSelector).should(Constants.EXISTS_ENABLED_VISIBLE);

        return new TreeNode(childSelector, true);
    }

    /**
     * @param expanded flag to wait on expanded or not expanded.
     */
    public void clickOnChevronAndWaitToToggleTo(final boolean expanded) {
        final SelenideElement chevron = $(chevronSelector);
        chevron.should(Constants.EXISTS_ENABLED_VISIBLE);
        chevron.click();

        Wait().until((webDriver -> isExpanded() == expanded));
    }

    /**
     * Expand the current tree node.
     */
    public void expand() {
        if (isExpanded()) {
            return;
        }

        clickOnChevronAndWaitToToggleTo(true);
    }

    /**
     * Collapse current tree node.
     */
    public void collapse() {
        if (!isExpanded()) {
            return;
        }

        clickOnChevronAndWaitToToggleTo(false);
    }

    /**
     * @return true if current tree node is selected.
     */
    public boolean isSelected() {
        return $(selectedNodeSelector).exists();
    }

    /**
     * Click on the tree node text.
     */
    public void clickOnText() {
        element().should(Constants.EXISTS_ENABLED_VISIBLE).click();
    }

    /**
     * Returns the selected node starting from this node.
     *
     * @return The selected first selected node, or null none exists.
     */
    public TreeNode selectedNode() {
        if (isSelected()) {
            return this;
        }
        if ($(selectorForSelectedNode()).exists()) {
            return new TreeNode(selectorForSelectedNode(), false);
        }
        return null;
    }

    /**
     * Returns the direct child node of this node by index.
     *
     * @param index - the 0 base index of the child node to look for
     * @return the node at the specified index or null.
     */
    public TreeNode getChildNodeByIndex(final int index) {
        final String selector = getSelectorForChildNodeAtIndex(index);
        if ($(selector).exists()) {
            return new TreeNode(selector, true);
        }
        return null;
    }
}
