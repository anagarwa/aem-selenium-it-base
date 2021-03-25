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

package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ImageToolbar extends BaseComponent {

    private static final String CSS_SELECTOR = ".imageeditor-toolbar";
    private static final String CSS_RATIO_BUTTON = ".imageeditor-popover-content button[value='%s']";

    public ImageToolbar() {
        super($$(CSS_SELECTOR).filterBy(Condition.visible).first());
    }

    public enum Action {

        SAVE("control#save"),
        FINISH("control#finish"),
        CLOSE("control#close"),
        UNDO("history#undo"),
        REDO("history#redo"),
        FULLSCREEN("fullscreen#start"),
        ENTER_FULLSCREEN("fullscreen#fullscreen"),
        FULLSCREEN_EXIT("fullscreen#fullscreenexit"),
        CROP_LAUNCH("crop#launch"),
        CROP_LAUNCHWITHRATIO("crop#launchwithratio"),
        ROTATE_RIGHT("rotate#right"),
        ROTATE_LEFT("rotate#left"),
        MAP_LAUNCH("map#launch"),
        ZOOM_RESET100("zoom#reset100"),
        ZOOM_POPUPSLIDER("zoom#popupslider");

        private final String action;

        Action(final String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }

        public String getSelector() {
            return "[data-action='" + action + "']";
        }
    }

    public ImageToolbar clickSaveButton() { return clickButton(ImageToolbar.Action.SAVE); }
    public ImageToolbar clickFinishButton() { return clickButton(Action.FINISH); }
    public ImageToolbar clickCloseButton() { return clickButton(ImageToolbar.Action.CLOSE); }

    public ImageToolbar clickUndoButton() { return clickButton(Action.UNDO); }
    public ImageToolbar clickRedoButton() { return clickButton(Action.REDO); }
    public ImageToolbar clickFullscreenButton() { return clickButton(Action.FULLSCREEN); }
    public ImageToolbar clickEnterFullscreenButton() { return clickButton(Action.ENTER_FULLSCREEN); }
    public ImageToolbar clickExitFullscreenButton() { return clickButton(Action.FULLSCREEN_EXIT); }

    public ImageToolbar clickCropLaunchButton() { return clickButton(Action.CROP_LAUNCH); }
    public ImageToolbar clickCropLaunchWithRatioButton() { return clickButton(Action.CROP_LAUNCHWITHRATIO); }

    public ImageToolbar clickRotateRightButton() { return clickButton(Action.ROTATE_RIGHT); }
    public ImageToolbar clickRotateLeftButton() { return clickButton(Action.ROTATE_LEFT); }

    public ImageToolbar clickMapLaunchButton() { return clickButton(Action.MAP_LAUNCH); }

    public ImageToolbar clickZoomReset100Button() { return clickButton(Action.ZOOM_RESET100); }
    public ImageToolbar clickZoomPopupsliderButton() { return clickButton(Action.ZOOM_POPUPSLIDER); }

    public ImageToolbar clickButton(Action action) {
        SelenideElement button = element().find(action.getSelector());
        button.shouldBe(Condition.visible);
        clickableClick(button);
        return this;
    }

    public SelenideElement getButton(Action action) {
        SelenideElement button = element().find(action.getSelector());
        button.should(Condition.exist);
        return button;
    }

    public ImageToolbar clickRatioButton(String value) {
        SelenideElement button = $(String.format(CSS_RATIO_BUTTON, value));
        button.shouldBe(Condition.visible);
        clickableClick(button);
        return this;
    }

    public SelenideElement getRatioButton(String value) {
        SelenideElement button = $(String.format(CSS_RATIO_BUTTON, value));
        button.should(Condition.exist);
        return button;
    }

}
