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

package com.adobe.cq.testing.selenium.pagewidgets.cq.actions;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

// All known actions
public final class EditorActions {

    private EditorActions() {
    }

    // Editor action bar items
    private static final SelenideElement PAGE_INFO = $("#pageinfo-trigger");
    private static final SelenideElement SIDE_PANEL_TOGGLE = $("#sidepanel-toggle-button");
    private static final SelenideElement UNLOCK_BUTTON = $("#unlock-page-trigger");

    /**
     * @return The Add Content button element.
     */
    public static SelenideElement getPageInfo() {
        return PAGE_INFO;
    }

    /**
     * @return The SidePanel Toggle button element.
     */
    public static SelenideElement getSidePanelToggle() {
        return SIDE_PANEL_TOGGLE;
    }

    /**
     * @return The unlock button element.
     */
    public static SelenideElement getUnlock() {
        return UNLOCK_BUTTON;
    }
}
