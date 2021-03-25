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

import com.adobe.cq.testing.selenium.pagewidgets.xf.VariationsPanel;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Component class representing the side panel in editor page.
 */
public final class SidePanel extends BaseSidePanel<SidePanel> {

    private static final String TAB_COMPONENTS_IDENTIFIER = "#SidePanel coral-tab[data-foundation-tracking-event*='components']";
    private static final String TAB_ASSETS_IDENTIFIER = "#SidePanel coral-tab[data-foundation-tracking-event*='assets']";
    private static final String TAB_CT_IDENTIFIER = "#SidePanel coral-tab[data-foundation-tracking-event*='content tree']";
    private static final String TAB_COMPONENTINSPECTOR_IDENTIFIER = "#SidePanel .cq-DeveloperRail coral-tab[data-foundation-tracking-event*='components']";
    private static final String TAB_ERRORS_IDENTIFIER = "#SidePanel coral-tab[data-foundation-tracking-event*='errors']";

    private static final String PANEL_ERRORS_IDENTIFIER = "coral-panel .sidepanel-tab-errorinspector";

    private SelenideElement tabAssets = $(TAB_ASSETS_IDENTIFIER);
    private SelenideElement tabComponents = $(TAB_COMPONENTS_IDENTIFIER);
    private SelenideElement tabCT = $(TAB_CT_IDENTIFIER);
    private SelenideElement tabComponentInspector = $(TAB_COMPONENTINSPECTOR_IDENTIFIER);
    private SelenideElement tabErrors = $(TAB_ERRORS_IDENTIFIER);
    private AssetsPanel     panelAssets = new AssetsPanel();
    private ComponentsPanel panelComponents = new ComponentsPanel();
    private VariationsPanel panelVariations = new VariationsPanel();
    private ContentTreePanel panelCT = new ContentTreePanel();
    private ComponentInspectorPanel panelComponentInspector = new ComponentInspectorPanel();
    private SelenideElement panelErrors = $(PANEL_ERRORS_IDENTIFIER);

    public SidePanel() {
        super();
    }

    /**
     * @return True if the Assets Panel is displayed, so open and visible.
     */
    public boolean isAssetsTabDisplayed() { return panelAssets.isVisible(); }

    /**
     * @return True if the Components Panel is displayed, so open and visible.
     */
    public boolean isComponentsTabDisplayed() { return panelComponents.isVisible(); }

    /**
     * @return True if the Variations Panel is displayed, so open and visible.
     */
    public boolean isVariationsTabDisplayed() { return panelVariations.isVisible(); }

    /**
     * @return True if the Content Tree Panel is displayed, so open and visible.
     */
    public boolean isContentTreeTabDisplayed() { return panelCT.isVisible(); }

    /**
     * @return True if the ComponentInspector Panel is displayed, so open and visible.
     */
    public boolean isComponentInspectorTabDisplayed() { return panelComponentInspector.isVisible(); }

    /**
     * @return True if the Errors Panel is displayed, so open and visible.
     */
    public boolean isErrorsTabDisplayed() { return panelErrors.isDisplayed(); }

    /**
     * @return The Assets Panel element.
     */
    public AssetsPanel getAssetsPanel() { return panelAssets; }

    /**
     * @return The Components Panel element.
     */
    public ComponentsPanel getComponentsPanel() { return panelComponents; }

    /**
     * @return The Variations Panel element.
     */
    public VariationsPanel getVariationsPanel() { return panelVariations; }

    /**
     * @return The Content Tree Panel element.
     */
    public ContentTreePanel getContentTreePanel() { return panelCT; }

    /**
     * @return The ComponentInspector Panel element.
     */
    public ComponentInspectorPanel getComponentInspectorPanel() { return panelComponentInspector; }

    /**
     * @return The Errors Panel element.
     */
    public SelenideElement getErrorsPanel() { return panelErrors; }

    /**
     * Click the Assets Tab to display the Assets Panel element.
     * @return The Assets Panel object.
     */
    public AssetsPanel selectAssetsTab() {
        show();
        tabAssets.click();
        panelAssets.waitVisible();
        return panelAssets;
    }

    /**
     * Click the Components Tab to display the Components Panel element.
     * @return The Components Panel object.
     */
    public ComponentsPanel selectComponentsTab() {
        show();
        tabComponents.click();
        panelComponents.waitVisible();
        return panelComponents;
    }

    /**
     * Click the Content Tree Tab to display the Content Tree Panel element.
     * @return The Content Tree Panel object.
     */
    public ContentTreePanel selectContentTreeTab() {
        show();
        tabCT.click();
        panelCT.waitVisible();
        return panelCT;
    }

    /**
     * Click the ComponentInspector Tab to display the ComponentInspector Panel element.
     * @return The Componentinspector Panel object.
     */
    public ComponentInspectorPanel selectComponentInspectorTab() {
        show();
        tabComponentInspector.click();
        panelComponentInspector.waitVisible();
        return panelComponentInspector;
    }

    /**
     * Click the Errors Tab to display the Errors Panel element.
     * @return The Errors Panel element.
     */
    public SelenideElement selectErrorsTab() {
        show();
        tabErrors.click();
        panelErrors.should(exist).shouldBe(visible);
        return panelErrors;
    }

}
