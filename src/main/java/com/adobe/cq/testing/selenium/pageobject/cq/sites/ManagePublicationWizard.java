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

package com.adobe.cq.testing.selenium.pageobject.cq.sites;

import com.adobe.cq.testing.selenium.pagewidgets.CalendarPicker;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralRadio;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.IncludeChildrenDialog;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public final class ManagePublicationWizard extends Wizard {

    private SelenideElement requestWorkflowTitle = new FormField("requestWorkflowTitle").getFullyDecoratedElement("input");
    private SelenideElement scheduleWorkflowTitle = new FormField("scheduleWorkflowTitle").getFullyDecoratedElement("input");
    private SelenideElement activateButton = $("button[value=\"Activate\"]");
    private SelenideElement deactivateButton = $("button[value=\"Deactivate\"]");

    /**
     * @return the element for the activate button.
     */
    public SelenideElement activate() {
        return activateButton;
    }

    /**
     * @return the element for the deactivate button.
     */
    public SelenideElement deactivate() {
        return deactivateButton;
    }

    /**
     * @return the scheduling radio group.
     */
    public CoralRadio scheduling() {
        return new CoralRadio(element(), "scheduling");
    }

    /**
     * @return workflow title field element.
     */
    public SelenideElement scheduleWorkflowTitle() {
        return scheduleWorkflowTitle;
    }

    /**
     * @return workflow title field element.
     */
    public SelenideElement requestWorkflowTitle() {
        return requestWorkflowTitle;
    }

    /**
     * @return the date picker field.
     */
    public CalendarPicker pickDate() {
        return new CalendarPicker("activationDate");
    }

    /**
     * @return possible actions in this wizard.
     */
    public ManagePublicationActions actions() {
        return new ManagePublicationActions();
    }

    // All known create workflow actions
    public static final class ManagePublicationActions {

        // Editor action bar items
        private static final SelenideElement ADD_CONTENT_BUTTON = $("[trackingelement=\"add content\"]");
        private static final SelenideElement INCLUDE_CHILDREN_BUTTON = $("[trackingelement=\"include children\"]");
        private static final SelenideElement REMOVE_SELECTION_BUTTON = $("[trackingelement=\"remove selection\"]");
        private static final SelenideElement PUBLISH_REFERENCES_BUTTON = $("[trackingelement=\"published references\"]");

        /**
         * The contructor for this helper.
         */
        private ManagePublicationActions() {
        }

        /**
         * Click on Add Content button and wait for the dialog.
         *
         * @return the picker that is displayed.
         */
        public ActionComponent<Picker> addContent() {
            return new ActionComponent(ADD_CONTENT_BUTTON, Picker::new, false);
        }

        /**
         * Click on Remove Selection button and wait for the dialog.
         *
         * @return the alert dialog that is displayed.
         */
        public ActionComponent<Dialog> removeSelection() {
            return new ActionComponent(REMOVE_SELECTION_BUTTON, () -> new Dialog("coral-dialog[role=\"alertdialog\"]"), false);
        }

        /**
         * Click on Include Children button and wait for the dialog.
         *
         * @return the include children dialog that is displayed.
         */
        public ActionComponent<IncludeChildrenDialog> includeChildren() {
            return new ActionComponent(INCLUDE_CHILDREN_BUTTON, IncludeChildrenDialog::new, false);
        }

        /**
         * Click on Include Children button and wait for the dialog.
         *
         * @return the include children dialog that is displayed.
         */
        public ActionComponent<Dialog> publishReferences() {
            return new ActionComponent(PUBLISH_REFERENCES_BUTTON, () -> new Dialog("coral-dialog.cq-common-publish-references-dialog"), false);
        }


    }

}
