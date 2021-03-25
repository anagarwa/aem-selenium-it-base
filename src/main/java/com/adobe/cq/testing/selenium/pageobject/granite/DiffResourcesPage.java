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

package com.adobe.cq.testing.selenium.pageobject.granite;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralActionBar;

import javax.annotation.Nonnull;

/**
 * The DiffResourcesPage
 */
public class DiffResourcesPage extends BasePage {

    private static final String DIFF_PAGE_PATH = "/libs/wcm/core/content/sites/diffresources.html";
    private static final String DIFF_PANEL_CONTENT = ".diff-panel-content";
    private final CoralActionBar actionbarElement;
    private final BaseComponent contentPanel;

    /**
     * Construct a DiffResourcesPage.
     */
    public DiffResourcesPage() {
        super(null, DIFF_PAGE_PATH);
        actionbarElement = new CoralActionBar();
        contentPanel = new BaseComponent(DIFF_PANEL_CONTENT);
    }

    /**
     * Open a DiffResourcesPage with path added as suffix
     *
     * @param pagePath The path to open without trailing
     * @param <T>      a concrete implementation class
     * @return self
     */
    @Override
    public <T extends BasePage> T open(@Nonnull String pagePath) {
        return super.open(DIFF_PAGE_PATH + pagePath);
    }

    /**
     * Action bar
     *
     * @return CoralActionBar
     */
    public CoralActionBar getActionbarElement() {
        return actionbarElement;
    }

    /**
     * Content panel
     *
     * @return BaseComponent
     */
    public BaseComponent getContentPanel() {
        return contentPanel;
    }

}
