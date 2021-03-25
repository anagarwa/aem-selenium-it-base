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

package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ReferencesPanel extends BaseComponent {

  private static final SelenideElement SECTION_BLUEPRINT = $("section.blueprint");
  private static final SelenideElement SECTION_LAUNCHES = $("section.launch");
  private static final SelenideElement SECTION_LIVE_COPIES = $("section.liveCopy");
  private static final SelenideElement SECTION_LANGUAGE_COPIES = $("section.languageCopy");
  private static final SelenideElement SECTION_INCOMING_LINKS = $("section.incomingLinks");
  private static final SelenideElement SECTION_BORROWED_CONTENT = $("section.borrowedContent");
  private static final SelenideElement SECTION_LENT_CONTENT = $("section.lentContent");
  private static final SelenideElement BACK_REFERENCES = $("div.granite-references-item--back");

  /**
   * Construct the Timeline Panel on the left Rail.
   */
  public ReferencesPanel() {
    super("coral-panel[data-shell-collectionpage-rail-panel=\"references\"]");
  }

  /**
   * @return true if this panel is selected and visible.
   */
  public boolean isOpened() {
    return element().has(Condition.cssClass("is-selected")) && element().isDisplayed();
  }

  /**
   * @return the blueprint section element.
   */
  public SelenideElement blueprint() {
    return SECTION_BLUEPRINT;
  }

  /**
   * @return the launches section element.
   */
  public SelenideElement launches() {
    return SECTION_LAUNCHES;
  }

  /**
   * @return the live copies section element.
   */
  public SelenideElement liveCopies() {
    return SECTION_LIVE_COPIES;
  }

  /**
   * @return the language copies section element.
   */
  public SelenideElement languageCopies() {
    return SECTION_LANGUAGE_COPIES;
  }

  /**
   * @return the incoming links section element.
   */
  public SelenideElement incomingLinks() {
    return SECTION_INCOMING_LINKS;
  }

  /**
   * @return the borrowed content section element.
   */
  public SelenideElement borrowedContent() {
    return SECTION_BORROWED_CONTENT;
  }

  /**
   * @return the lent content section element.
   */
  public SelenideElement lentContent() {
    return SECTION_LENT_CONTENT;
  }

  /**
   * @return the element which represent the back action.
   */
  public SelenideElement back() {
    return BACK_REFERENCES;
  }

  /**
   * @return list of references items.
   */
  public ElementsCollection items() {
    return element().$$(".detail-list section.granite-references-item");
  }

  /**
   * @return the reveal in sites element.
   */
  public SelenideElement reveal() {
    return element().$("button.granite-Reference-Action--reveal");
  }

  /**
   * @return the synchronize live copy action element.
   */
  public SelenideElement rollout() {
    return element().$("button.live-copy-rollout");
  }

  /**
   * @return the edit live copy action element.
   */
  public SelenideElement editLiveCopy() {
    return element().$("button.live-copy-edit");
  }

  /**
   * @return the compare live copy action element.
   */
  public SelenideElement compareLiveCopy() {
    return element().$("a.live-copy-compare");
  }

  /**
   * @return the launch edit properties action element.
   */
  public SelenideElement editLaunchProperties() {
    return element().$("a[href*=\"/properties.html?\"]");
  }

  /**
   * @return the launch edit properties action element.
   */
  public SelenideElement compareLaunch() {
    return element().$("a[href*=\"/diffresources.html/\"]");
  }

}
