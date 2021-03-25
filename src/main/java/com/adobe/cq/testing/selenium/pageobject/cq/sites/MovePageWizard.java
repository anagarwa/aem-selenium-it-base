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

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.adobe.cq.testing.selenium.pagewidgets.granite.columnview.ColumnView;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.codeborne.selenide.Selenide.$;

public class MovePageWizard extends Wizard {

  private static final String MOVE_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/movepagewizard.html";

  private static final SelenideElement TITLE_FIELD = new FormField("./jcr:content/jcr:title")
      .getFullyDecoratedElement("coral-panel.is-selected input");
  private static final SelenideElement NAME_FIELD = new FormField("name")
      .getFullyDecoratedElement("coral-panel.is-selected input");
  private static final Dialog CONFIRM_DIALOG = new Dialog("coral-dialog");

  private static final ColumnView COLUMN_VIEW = new ColumnView("coral-panel.is-selected coral-columnview");

  /**
   * @return true if the wizard is opened.
   */
  public boolean isOpened() {
    return WebDriverRunner.url().contains(MOVE_WIZARD_URL);
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
   * @return the column view collection.
   */
  public Collection collection() {
    return COLUMN_VIEW;
  }

  /**
   * @return the associated list on adjust / republish step.
   */
  public ListReferencing list() {
    return new ListReferencing();
  }

  /**
   * @return the Info Details accessor.
   */
  public InfoDetails infoDetails() {
    return new InfoDetails();
  }

  /**
   * @return the confirm dialog displayed at last step.
   */
  public Dialog confirmDialog() {
    return CONFIRM_DIALOG;
  }

  public class InfoDetails extends BaseComponent {

    private final ElementsCollection labels;

    /**
     * Construct a wrapper for the referencing list div.
     */
    public InfoDetails() {
      super("section.info-details");
      labels = element().$$("label");
    }

    /**
     * @return the element that display the Referencing Pages.
     */
    public SelenideElement getReferencingPages() {
      return findElement("REFERENCING PAGES");
    }

    /**
     * @return true if References limit notice exists.
     */
    public boolean isRefLimitExceeded() {
      return $(".references .notice").exists();
    }

    /**
     * @return the element that display the Created On.
     */
    public SelenideElement getCreatedOn() {
      return findElement("CREATED ON");
    }

    /**
     * @return the element that display the Created By.
     */
    public SelenideElement getCreatedBy() {
      return findElement("CREATED BY");
    }

    /**
     * @return the element that display the Path.
     */
    public SelenideElement getPath() {
      return findElement("PATH");
    }

    private SelenideElement findElement(final String detailTitle) {
      for (int i = 0; i < labels.size(); i++) {
        if (labels.get(i).getText().equals(detailTitle)) {
          return labels.get(i + labels.size() / 2);
        }
      }
      return null;
    }

  }

  public class ListReferencing extends BaseComponent {

    private final ElementsCollection items;

    /**
     * Construct a wrapper for the referencing list div.
     */
    public ListReferencing() {
      super("div.list-referencing");
      items = element().$$("tr[data-path]");
    }

    /**
     * @return the checkbox for the global adjust.
     */
    public CoralCheckbox adjust() {
      return new CoralCheckbox(element().$(".select-adjust").getSearchCriteria());
    }

    /**
     * @return the checkbox for the global adjust.
     */
    public CoralCheckbox republish() {
      return new CoralCheckbox(element().$(".select-republish").getSearchCriteria());
    }

    /**
     * @param path for the expected item to be returned.
     * @return the corresponding element.
     */
    public SelenideElement getListItem(final String path) {
      return element().$(String.format("tr[data-path=\"%s\"]", path));
    }

    /**
     * @param path for the expected item to be present.
     * @return the element if exist, otherwise it will fail due to timeout.
     */
    public SelenideElement waitForItem(final String path) {
      return getListItem(path).should(EXISTS_ENABLED_VISIBLE);
    }

    /**
     * @param path for the expected item to toggle the checkbox.
     */
    public void toggleItem(final String path) {
      new CoralCheckbox(waitForItem(path)).click();
    }

    /**
     * @param path for the expected item to toggle the checkbox.
     * @return true if checked is true on the item.
     */
    public boolean isChecked(final String path) {
      return new CoralCheckbox(waitForItem(path)).isChecked();
    }

    /**
     * @return all the rows from the list as elements.
     */
    public ElementsCollection items() {
      return items;
    }
  }

}
