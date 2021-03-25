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

package com.adobe.cq.testing.selenium.pagewidgets.cfm;

import static com.codeborne.selenide.Selenide.$;
import javax.annotation.Nonnull;

import com.adobe.cq.testing.selenium.pageobject.cq.dam.AssetsPage;
import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralActionBar;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.sidepanel.CFEditorSidePanel;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class CFMetadataPage extends BasePage {

	private static final String CF_METADATA_EDITOR_BASE_URL = "/mnt/overlay/dam/cfm/admin/content/v2/metadata-editor.html";
	private final SelenideElement cfMetadataFormElement = $("#aem-assets-metadataeditor-formid");
	private final CoralActionBar coralActionBar = new CoralActionBar();

	public CFMetadataPage() {
		super(CF_METADATA_EDITOR_BASE_URL);
	}

	@SuppressWarnings("unchecked")
	public CFMetadataPage open(@Nonnull final String path) {
		return super.open(CF_METADATA_EDITOR_BASE_URL + path);
	}

	public ActionComponent<AssetsPage> save() {
		SelenideElement saveBtn = coralActionBar.element().$("button.button-apply");
		return new ActionComponent<>(saveBtn, AssetsPage::new, true);
	}

	/**
	 * Returns the sidepanel of the MetadataPage
	 * 
	 * @return The sidepanel
	 */
	public CFEditorSidePanel sidePanel() {
		return new CFEditorSidePanel();
	}

	/**
	 * Returns the title input element (input[name=\"./jcr:content/jcr:title\"])
	 * 
	 * @return The title element
	 */
	public SelenideElement title() {
		return cfMetadataFormElement.$("input[name=\"./jcr:content/jcr:title\"]").should(Condition.exist);
	}

}
