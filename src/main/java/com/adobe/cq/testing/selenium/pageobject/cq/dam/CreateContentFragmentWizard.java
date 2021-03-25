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

import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Masonry;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.TITLE;

public class CreateContentFragmentWizard extends Wizard {

  private static final String CREATE_WIZARD_URL = "/mnt/overlay/dam/cfm/admin/content/v2/createfragment.html";

  private static final Collection COLLECTION = new Masonry(".foundation-collection");
  private static final Collection DESTINATION_COLLECTION = new Masonry("#cq-dam-cfm-destination-collection.foundation-collection");
  private static final Collection MODEL_COLLECTION = new Masonry(".new-content-fragment-models-collection .foundation-collection");
  private static final String PANEL_IS_SELECTED = "coral-panel.is-selected ";

  private static final SelenideElement TITLE_FIELD = TITLE.getFullyDecoratedElement(PANEL_IS_SELECTED);
  private static final SelenideElement DESCRIPTION_FIELD = new FormField("description").getFullyDecoratedElement(PANEL_IS_SELECTED);

  private static final SelenideElement NAME_FIELD = new FormField("name").getFullyDecoratedElement(PANEL_IS_SELECTED);
  private static final AutoCompleteField<Picker> TAGS_FIELD = new AutoCompleteField<Picker>("tags", Picker::new, false);
  private static final Dialog CONFIRM_DIALOG = new Dialog("coral-dialog");

  /**
   * @return true if the wizard is opened.
   */
  public boolean isOpened() {
    return WebDriverRunner.url().contains(CREATE_WIZARD_URL);
  }

  /**
   * @param path destination folder for new Content Fragment
   * @return self
   */
  public CreateContentFragmentWizard selectDestination(final String path) {
    DESTINATION_COLLECTION.waitVisible();
    DESTINATION_COLLECTION.selectItem(path);
    return this;
  }

  /**
   * @param modelId the id of the model (path in conf usually).
   * @return self
   */
  public CreateContentFragmentWizard selectModel(final String modelId) {
    MODEL_COLLECTION.waitVisible();
    MODEL_COLLECTION.selectItem(modelId);
    return this;
  }

  /**
   * @param path destination folder for new Content Fragment
   * @return true if path is listed in the destination collection.
   */
  public boolean hasDestination(final String path) {
    DESTINATION_COLLECTION.waitVisible();
    return DESTINATION_COLLECTION.hasCollectionItem(path);
  }

  /**
   * @param modelId the id of the model (path in conf usually).
   * @return true if model is listed in the collection.
   */
  public boolean hasModel(final String modelId) {
    MODEL_COLLECTION.waitVisible();
    return MODEL_COLLECTION.hasCollectionItem(modelId);
  }

  /**
   * Activate the html element associated with the provided collection item id.
   * @param itemId the id of the collection item to whom we want to activate
   */
  public CreateContentFragmentWizard activateItem(final String itemId) {
    COLLECTION.activate(itemId);
    return this;
  }

  /**
   * Activate the html element associated with the provided destination collection item id.
   * @param itemId the id of the collection item to whom we want to activate
   */
  public CreateContentFragmentWizard activateDestinationItem(final String itemId) {
    DESTINATION_COLLECTION.activate(itemId);
    return this;
  }

  /**
   * Activate the html element associated with the provided model collection item id.
   * @param itemId the id of the collection item to whom we want to activate
   */
  public CreateContentFragmentWizard activateModelItem(final String itemId) {
    MODEL_COLLECTION.activate(itemId);
    return this;
  }

  /**
   * @return Return true if the item is Selectable.
   * @param collectionItemID id of the item, as indicated by `data-foundation-collection-item-id`.
   */
  public boolean isItemSelectable(final String collectionItemID) {
    return COLLECTION.isSelectable(collectionItemID);
  }

  /**
   * @return the title field.
   */
  public SelenideElement title() {
    return TITLE_FIELD;
  }

  /**
   * @return the name field.
   */
  public SelenideElement name() {
    return NAME_FIELD;
  }

  /**
   * @return the description field.
   */
  public SelenideElement description() {
    return DESCRIPTION_FIELD;
  }

  /**
   * @return the siteOwner field.
   */
  public AutoCompleteField tags() {
    return TAGS_FIELD;
  }

  /**
   * @return the confirm dialog displayed at last step.
   */
  public Dialog confirmDialog() {
    return CONFIRM_DIALOG;
  }

}
