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

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Masonry;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.codeborne.selenide.SelenideElement;

import java.util.Arrays;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public final class AssetsPanel extends BaseComponent {

    private static final SelenideElement FILTER_FIELD = new FormField("2_fulltext").getDecoratedElement();
    private static final SelenideElement CLEAR_FILTER_BUTTON = $("button[variant=\"minimal\"]");
    private static final CoralSelect ASSET_FILTER_TYPE = new CoralSelect("name=\"assetfilter_type_selector\"");

    public AssetsPanel() {
        super("coral-panel .editor-AssetFinder");
    }

    public void dropOnPage(final String assertResourceCard, final String pageEditableResource) {
        final SelenideElement assetCard = $("coral-card[data-path='" + assertResourceCard + "']");
        final SelenideElement dropTarget = $$(".cq-droptarget[data-path='" + pageEditableResource + "']").last();
        Helpers.dragOnPage(assetCard, dropTarget);
    }

    public void setFilter(final String filter) {
        FILTER_FIELD.sendKeys(filter);
        FILTER_FIELD.pressEnter();
    }

    public void clearFilter() {
        clickableClick(CLEAR_FILTER_BUTTON);
    }

    public void selectType(final AssetsFilterType type) {
        ASSET_FILTER_TYPE.click();
        ASSET_FILTER_TYPE.selectList().selectByLabel(type.getValue());
    }

    public void setFilterPath(final String path) {
        AssetsFilterType currentAssetsFilterType = getCurrentAssetFilterType();
        if (currentAssetsFilterType != null) {
            String pathFilterName = currentAssetsFilterType.getPathFilterName();
            if (!"".equals(pathFilterName)) {
                AutoCompleteField filterPathField = new AutoCompleteField(
                        new FormField(pathFilterName).getName(),
                        () -> new Picker("coral-dialog"), false);
                filterPathField.sendKeys(path);
                filterPathField.buttonlist().selectByValue(path);
            }
        }
    }

    public AutoCompleteField<Picker> getFilterTags() {
        String tagFilterName = "";
        AssetsFilterType currentAssetsFilterType = getCurrentAssetFilterType();
        if (currentAssetsFilterType != null) {
            tagFilterName = currentAssetsFilterType.geTagFilterName();
        }
        return new AutoCompleteField(
                new FormField(tagFilterName).getName(),
                () -> new Picker("coral-dialog"), false);
    }

    public Collection collection() {
        Masonry masonry = new Masonry(getCssSelector() + " coral-masonry");
        masonry.setItemSelectorPattern("%s coral-card[data-path='%s']");
        masonry.setAllItemsSelector("%s coral-card");
        return masonry;
    }

    public AssetsFilterType getCurrentAssetFilterType() {
        if (ASSET_FILTER_TYPE.selectedItem().exists()) {
            return AssetsFilterType.findByValue(ASSET_FILTER_TYPE.selectedItem().getValue());
        } else {
            // if there is no asset filter type set yet provide the default one for image
            return AssetsFilterType.IMAGES;
        }
    }

    public enum AssetsFilterType {
        IMAGES("Images", "assetfilter_image_path", "jcr:content/metadata/cq:tags"),
        VIDEOS("Videos", "assetfilter_video_path", "jcr:content/metadata/cq:tags"),
        DOCUMENTS("Documents", "assetfilter_document_path"),
        PARAGRAPHS("Paragraphs", "assetfilter_paragraph_path"),
        CONTENT_FRAGMENTS("Content Fragments"),
        EXPERIENCE_FRAGMENTS("Experience Fragments", "assetfilter_xf_path", "jcr:content/cq:tags"),
        PAGES("Pages", "assetfilter_page_path"),
        DESIGN_PACKAGES("Design Packages", "assetfilter_adaptiveDocument_path"),
        MANUSCRIPT("Manuscript", "jcr:content/metadata/cq:tags"),
        THREE_DIMENSIONS("3D");

        private final String value;
        private final String pathFilterName;
        private final String tagFilterName;

        private AssetsFilterType(final String value) {
            this(value, "");
        }

        private AssetsFilterType(final String value, final String pathFilterName) {
            this(value, pathFilterName, "");
        }

        private AssetsFilterType(final String value, final String pathFilterName, final String tagFilterName) {
            this.value = value;
            this.pathFilterName = pathFilterName;
            this.tagFilterName = tagFilterName;
        }

        public static AssetsFilterType findByValue(final String value) {
            return Arrays.stream(values()).filter(type -> type.getValue().equals(value)).findFirst().orElse(null);
        }

        public String getValue() {
            return value;
        }

        public String getPathFilterName() {
            return pathFilterName;
        }

        public String geTagFilterName() {
            return tagFilterName;
        }

    }

}