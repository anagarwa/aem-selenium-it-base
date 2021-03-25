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

package com.adobe.cq.testing.selenium.pagewidgets.xf;

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.SelenideElement;

import java.util.Optional;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

public class PropertiesCloudServicesTab {

    private static final long NETWORK_POLLING_INTERVAL = 250;

    private SelenideElement cloudServiceConfigurationsSelect = $("coral-select.cq-CloudServices-serviceSelect [handle=button]");

    private SelenideElement adobeTargetSelectItem = $("coral-selectlist-item[value=\"/etc/cloudservices/testandtarget\"]");

    private SelenideElement adobeTargetSelect = $("coral-select.cq-CloudServices-configSelect [handle=button]");

    private SelenideElement workspaceSelect = $("coral-select.cq-adobe-target-export-workspace [handle=button]");

    private SelenideElement saveButton = $("button#shell-propertiespage-doneactivator");

    private SelenideElement inheritanceCheckbox = $("coral-checkbox.cq-CloudServices-inheritance");

    public PropertiesCloudServicesTab(final String panelId) {
    }

    public void addAdobeTargetCloudServiceConfiguration() {
        clickableClick(cloudServiceConfigurationsSelect);
        Helpers.waitNetworkIdled(NETWORK_POLLING_INTERVAL);
        clickableClick(adobeTargetSelectItem);
        Helpers.waitNetworkIdled(NETWORK_POLLING_INTERVAL);
    }

    public void selectTntConfig(String adobeTargetConfigName) {
        clickableClick(adobeTargetSelect);
        Helpers.waitNetworkIdled(NETWORK_POLLING_INTERVAL);
        SelenideElement tntTargetSelectItem = $("coral-selectlist-item[value=\"/etc/cloudservices/testandtarget/" + adobeTargetConfigName + "\"]");
        clickableClick(tntTargetSelectItem);
        Helpers.waitNetworkIdled(NETWORK_POLLING_INTERVAL);
    }

    public void selectWorkspace(String workspaceID) {
        clickableClick(workspaceSelect);
        Helpers.waitNetworkIdled(NETWORK_POLLING_INTERVAL);
        SelenideElement workspaceSelectItem = $("coral-selectlist-item[value=\"" + workspaceID + "\"]");
        clickableClick(workspaceSelectItem);
        Helpers.waitNetworkIdled(NETWORK_POLLING_INTERVAL);
    }

    public void saveChanges() {
        ExpectNav.on(() -> {
            clickableClick(saveButton);
        });
    }

    public boolean workspaceSelectIsEnabled() {
        return !Optional.ofNullable(workspaceSelect.getAttribute("disabled")).isPresent();
    }

    public String workspaceSelectGetSelectedValue() {
        SelenideElement selectedOption = $("coral-select.cq-adobe-target-export-workspace coral-select-item[selected]");
        return  selectedOption.getAttribute("value");
    }

    public void breakInheritance() {
        clickableClick(inheritanceCheckbox);
    }
}