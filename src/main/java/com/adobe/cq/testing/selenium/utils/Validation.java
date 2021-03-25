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

package com.adobe.cq.testing.selenium.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public final class Validation {

    public static final String ALERTING_CHARS = "%/\\:*?\"[]|.#{};+ ";

    private static final String INVALID_CHARS_MESSAGE = "This field must only contain letters, numbers, dashes, commas and underscores.";

    /**
     * @param nameField input field to set value on
     */
    public static void assertInvalidChars(final SelenideElement nameField) {
        ALERTING_CHARS.chars().forEach(c -> {
            nameField.setValue("");
            nameField.sendKeys(String.format("check%c", c));
            nameField.shouldHave(Condition.cssClass("is-invalid"));
            nameField.parent().shouldBe(Condition.visible)
                    .shouldHave(Condition.matchText(INVALID_CHARS_MESSAGE));
        });
    }
}
