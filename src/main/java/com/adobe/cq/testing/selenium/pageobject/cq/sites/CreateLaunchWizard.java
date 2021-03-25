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
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralRadio;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Switch;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreateLaunchWizard extends Wizard {

  private static final String CREATE_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/createlaunchwizard.html";

  private static final SelenideElement TITLE_FIELD = new FormField("title")
      .getFullyDecoratedElement("coral-panel.is-selected input");
  private static final CoralCheckbox IS_LIVE_COPY_FIELD = new CoralCheckbox("input[\"isLiveCopy\"]");
  private static final CoralRadio CREATE_OPTIONS = new CoralRadio($("coral-panel.is-selected"), "page");
  private static final CalendarPicker LAUNCH_DATE_PICKER = new CalendarPicker("liveDate");
  private static final Dialog CONFIRM_DIALOG = new Dialog("coral-dialog");
  private static final ElementsCollection SWITCHES = $$("coral-panel.is-selected coral-switch");

  /**
   * @return true if the wizard is opened.
   */
  public boolean isOpened() {
    return WebDriverRunner.url().contains(CREATE_WIZARD_URL);
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
  public CoralRadio launchOptions() {
    return CREATE_OPTIONS;
  }

  /**
   * @return the liveDate calendar picker field.
   */
  public CalendarPicker liveDate() {
    return LAUNCH_DATE_PICKER;
  }

  /**
   * @return the is live copy checkbox field.
   */
  public CoralCheckbox isLiveCopy() {
    return IS_LIVE_COPY_FIELD;
  }

  /**
   * @return the confirm dialog displayed at last step.
   */
  public Dialog confirmDialog() {
    return CONFIRM_DIALOG;
  }

  /**
   * @return the is live copy checkbox field.
   */
  public Stream<Switch> switches() {
    return SWITCHES.stream().map(Switch::new);
  }

  /**
   * Wait until it has switches on the page.
   */
  public void waitForSwitches() {
    SWITCHES.shouldHave(CollectionCondition.sizeGreaterThan(0));
  }

  /**
   * @param parent the parent page on which to create the launch.
   * @param source the source page for this launch.
   * @return the page object for this wizard.
   */
  public static CreateLaunchWizard open(final String parent, final String source) {
    return new CreateLaunchWizard().open(String.format("%s%s?source=%s", CREATE_WIZARD_URL, parent, source));
  }

}
