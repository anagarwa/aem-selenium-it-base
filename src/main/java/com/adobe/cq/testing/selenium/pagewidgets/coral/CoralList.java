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

import static com.codeborne.selenide.Selenide.Wait;

public final class CoralList extends BaseComponent {

    private static final String SELECTOR_ITEM_ELEMENT = "coral-list-item";

    /**
     * @param cssSelector the cssSelector for filtering on coral-list elements.
     */
    public CoralList(final String cssSelector) {
        super(String.format("coral-list%s", cssSelector));
    }

    /**
     * @param attribute attribute selector string.
     * @param value value to match with.
     * @return the stream of CoralListItem that matches.
     */
    public Stream<CoralListItem> getItemsByAttribute(final String attribute, final String value) {
        return items().filter(e -> value.equals(e.element().attr(attribute)));
    }

    /**
     * @return the selected item element or null if it doesn't exist.
     */
    public CoralListItem selectedItem() {
        return new CoralListItem(element().$(String.format("%s[selected]", SELECTOR_ITEM_ELEMENT)));
    }

    /**
     * Determines if an item exists in the list.
     *
     * @param attribute attribute selector string.
     * @param value value to match with.
     * @return true if the list contains the attribute / value pairs.
     */
    public boolean hasItemByAttribute(final String attribute, final String value) {
        return items().anyMatch(e -> value.equals(e.element().attr(attribute)));
    }

    /**
     * @return a stream of CoralListItem from this list.
     */
    public Stream<CoralListItem> items() {
        return element().$$(SELECTOR_ITEM_ELEMENT).stream().map(CoralListItem::new);
    }

    /**
     * Poll the list for any item with matching value for given attribute.
     * @param attribute attribute name
     * @param expectedValue expected value for this attribute.
     */
    public void waitForItemByAttribute(final String attribute, final String expectedValue) {
        Wait().until(webDriver -> {
            return hasItemByAttribute(attribute, expectedValue);
        });
    }

    public class CoralListItem extends BaseComponent {

        private CoralListItem(SelenideElement element) {
            super(element);
        }

    }

}
