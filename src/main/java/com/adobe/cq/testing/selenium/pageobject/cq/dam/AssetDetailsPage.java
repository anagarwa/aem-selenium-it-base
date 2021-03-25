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

package com.adobe.cq.testing.selenium.pageobject.cq.dam;

import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Rail;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

/**
 * The assets main page.
 */
public class AssetDetailsPage extends BasePage {

    private static final String BASE_PATH = "/assetdetails.html";

    /**
     * Default constructor.
     */
    public AssetDetailsPage() {
        super(BASE_PATH);
    }

    /**
     * Access to the left rail.
     *
     * @return RailComponent.
     */
    public Rail leftRail() {
        // todo was using "coral-cyclebutton.granite-toggleable-control"
        return new Rail();
    }

    /**
     * Open assets page.
     *
     * @param path - optional additional path to the default page.
     */
    @Override
    public AssetDetailsPage open(@Nonnull final String path) {
        if (StringUtils.isNotBlank(path)) {
            super.open(BASE_PATH + path);
        } else {
            super.open();
        }
        return this;
    }
}
