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

import com.adobe.cq.testing.selenium.pageobject.cq.sites.CreateLiveCopyWizard;
import com.adobe.cq.testing.selenium.pageobject.granite.CollectionPage;
import com.adobe.cq.testing.selenium.pagewidgets.cq.CreateDAMFolderDialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.actions.CreateActions;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;

import javax.annotation.Nonnull;
import java.io.File;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * The assets main page.
 */
public class AssetsPage extends CollectionPage {

    private static final String BASE_PATH = "/assets.html";

    /**
     * Default constructor.
     */
    public AssetsPage() {
        super(BASE_PATH + "/content/dam");
    }

    /**
     * Open custom assets page.
     *
     * @param path - optional additional path to the default page.
     * @return this shell page.
     */
    @Override
    public AssetsPage open(@Nonnull final String path) {
        if (StringUtils.isNotBlank(path)) {
            super.open(BASE_PATH + path);
        } else {
            super.open();
        }
        return this;
    }

    /**
     * Determines if asset is visible on current page.
     * @param assetPath - relative path to asset
     * @return true if whatever asset is present or not
     */
    public boolean isAssetPresent(final String assetPath) {
        // @todo: handle this using the CollectionComponent
        return $(String.format(".foundation-collection-item[data-foundation-collection-item-id=\"%s\"]", assetPath)).is(
            Condition.visible);
    }

    /**
     * Toggle the accordion in the search filter.
     * @param accordionNumber - The accordion number
     */
    // @todo create a filterComponent and move code there
    public void toggleFilterAccordion(final int accordionNumber) {
        final String selector = "coral-overlay[open] coral-panelstack coral-accordion:nth-of-type(" + accordionNumber + ")"
            + " coral-accordion-item > div:first-child";
        $(selector).shouldBe(EXISTS_ENABLED_VISIBLE).click();
    }

    /**
     * @return a AssetsCreateAction which can be used to trigger create actions from right side.
     */
    public CreateActions createAction() {
        return new CreateActions();
    }

    /**
     * @return associated combo action for this assets page.
     */
    public AssetsPageSmartActions smartActions() {
        return new AssetsPageSmartActions(this);
    }

    /**
     * Common actions for Sites.
     */
    public static final class AssetsPageSmartActions {

        private final AssetsPage assetsPage;

        private AssetsPageSmartActions(final AssetsPage context) {
            assetsPage = context;
        }

        /**
         * @param type action type to use
         * @return the action element.
         */
        private SelenideElement getCreateAction(final CreateActions.DAMCreateActionType type) {
            // Click on create action
            final CreateActions action = assetsPage.createAction();
            action.click();

            // Click on Folder
            return action.getAction(type);
        }

        /**
         * Combo action to open the create folder dialog from current context.
         * @return the create folder dialog.
         */
        public CreateDAMFolderDialog openCreateFolderDialog() {
            // Click on create action
            final SelenideElement createAction = getCreateAction(CreateActions.DAMCreateActionType.FOLDER);
            clickableClick(createAction);

            return new CreateDAMFolderDialog();
        }

        /**
         * Combo action to open the create site wizard from current context.
         */
        public void openUploadFiles(File... filesPath) {
            // Click on create action
            final SelenideElement createAction = getCreateAction(CreateActions.DAMCreateActionType.UPLOAD_FILES);
            createAction.uploadFile(filesPath);
        }

        /**
         * Combo action to open the create site wizard from current context.
         * @return the create site wizard.
         */
        public CreateContentFragmentWizard openCreateContentFragmentWizard() {
            final SelenideElement createAction = getCreateAction(CreateActions.DAMCreateActionType.CONTENT_FRAGMENT);
            ExpectNav.on(() -> clickableClick(createAction));

            CreateContentFragmentWizard wizard = new CreateContentFragmentWizard();
            wizard.waitReady();
            return wizard;
        }

        /**
         * Combo action to open the create workflow wizard from current context.
         * @return the create workflow wizard.
         */
        public CreateLiveCopyWizard openCreateLiveCopyWizard() {
            final SelenideElement createAction = getCreateAction(CreateActions.DAMCreateActionType.LIVE_COPY);
            ExpectNav.on(() -> clickableClick(createAction));
            CreateLiveCopyWizard wizard = new CreateLiveCopyWizard();
            wizard.waitReady();
            return wizard;
        }

    }

    public AssetsPageToolbarActions actions() {
        return new AssetsPageToolbarActions();
    }

    // Helper Classes for Asstes Console
    public static final class AssetsPageToolbarActions {

        private static final String POPOVER = "coral-popover-content ";
        private static final String QUICK_PUBLISH = ".cq-damadmin-admin-actions-publish-activator";
        private static final String QUICK_PUBLISH_IN_POPOVER = POPOVER + QUICK_PUBLISH;

        private static final SelenideElement quickPublish = $(QUICK_PUBLISH);
        private static final SelenideElement quickPublishInPopover = $(QUICK_PUBLISH_IN_POPOVER);
        private static final SelenideElement more = $("button[coral-actionbar-more]");

        // Click "more action" to display all actions
        public void showMoreActions() {
            if (more.exists() && more.isDisplayed()) {
                more.click();
            }
        }

        public ActionComponent<Dialog> quickPublish() {
            SelenideElement el = quickPublish;
            if (!quickPublish.isDisplayed()) {
                el = quickPublishInPopover;
                this.showMoreActions();
            }
            return new ActionComponent<>(el, Dialog::new, true);
        }

    }

}
