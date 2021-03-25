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


package com.adobe.cq.testing.selenium.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.TimeoutException;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.hasWithPolling;
import static com.codeborne.selenide.Selenide.$;

/**
 * Editor page class representing Template Editor.
 */
public final class TemplateEditorPage extends EditorPage {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateEditorPage.class);

    private SelenideElement structureModeButton = $("button[is='coral-buttonlist-item'][data-layer='structure']");
    private SelenideElement structureLayerButton = $("button.editor-GlobalBar-item[data-layer='structure']");

    private SelenideElement initialModeButton = $("button[is='coral-buttonlist-item'][data-layer='initial']");
    private SelenideElement initialLayerButton = $("button.editor-GlobalBar-item[data-layer='initial']");

    private SelenideElement layoutModeButton = $("button[is='coral-buttonlist-item'][data-layer='Layouting']");
    private SelenideElement layoutLayerButton = $("button.editor-GlobalBar-item[data-layer='Layouting']");

    public TemplateEditorPage(URI base, String pagePath) {
        super(base, pagePath + "/structure");
    }

    public TemplateEditorPage(String pagePath) {
        super(pagePath + "/structure");
    }

    /**
     * Switch to the Structure Mode of Template Editor.
     * @return self
     * @throws TimeoutException if something wrong occurred
     */
    public TemplateEditorPage enterStructureMode() throws TimeoutException {
        return enterMode(structureModeButton, structureLayerButton);
    }

    /**
     * Switch to the Initial Content Mode of Template Editor.
     * @return self
     * @throws TimeoutException if something wrong occurred
     */
    public TemplateEditorPage enterInitialMode() throws TimeoutException {
        return enterMode(initialModeButton, initialLayerButton);
    }

    /**
     * Switch to the Layout Mode of Template Editor.
     * @return self
     * @throws TimeoutException if something wrong occurred
     */
    public TemplateEditorPage enterLayoutMode() throws TimeoutException {
        return enterMode(layoutModeButton, layoutLayerButton);
    }

    public boolean isInStructureMode() {
        return hasWithPolling(structureLayerButton, Condition.visible) &&
                hasWithPolling(getOverlayWrapper(), Condition.visible) &&
                !getOverlayWrapper().is(Condition.cssClass("is-hidden"));
    }

    public boolean isInInitialMode() {
        return hasWithPolling(initialLayerButton, Condition.visible) &&
                hasWithPolling(getOverlayWrapper(), Condition.visible) &&
                !getOverlayWrapper().is(Condition.cssClass("is-hidden"));
    }

    public boolean isInLayoutMode() {
        return hasWithPolling(layoutLayerButton, Condition.visible) &&
                hasWithPolling(getOverlayWrapper(), Condition.visible) &&
                !getOverlayWrapper().is(Condition.cssClass("is-hidden"));
    }

}
