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


package com.adobe.cq.testing.selenium.pageobject.cq.xf;

import com.adobe.cq.testing.selenium.pageobject.cq.sites.MovePageWizard;
import com.adobe.cq.testing.selenium.pageobject.cq.sites.PropertiesPage;
import com.adobe.cq.testing.selenium.pageobject.granite.CollectionPage;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.xf.XFCreateAction;
import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Selenide.$;

public class XFConsolePage extends CollectionPage {

  private static final String BASE_PATH = "/aem/experience-fragments.html";

  /**
   * Default constructor.
   */
  public XFConsolePage() {
    super(BASE_PATH);
  }

  /**
   * Open custom assets page.
   * @param path - optional additional path to the default page.
   * @return SitesPage.
   */
  @Override
  public XFConsolePage open(@Nonnull final String path) {
    super.open(String.format("%s%s", BASE_PATH, path));
    return this;
  }

  /**
   * @return SitesPage opened on default location.
   */
  @Override
  public XFConsolePage open() {
    super.open();
    return this;
  }

  /**
   * @return associated combo action for this sites page.
   */
  public XFConsoleToolbarActions actions() {
    return new XFConsoleToolbarActions();
  }

  /**
   * @return associated combo action for this sites page.
   */
  public XFCreateAction create() {
    XFCreateAction createAction = new XFCreateAction();
    createAction.click();
    return createAction;
  }

  // All known actions
  public static final class XFConsoleToolbarActions {

    private XFConsoleToolbarActions() {
    }

    // Sites action bar items
    private static SelenideElement deselect = $("button.granite-collection-deselect");
    private static SelenideElement create = $("button.cq-siteadmin-admin-actions-create-activator");
    private static SelenideElement edit = $("button.cq-siteadmin-admin-actions-edit-activator");
    private static SelenideElement properties = $("button.cq-siteadmin-admin-actions-properties-activator");
    private static SelenideElement folderProperties = $("button.cq-siteadmin-admin-actions-folderproperties-activator");
    private static SelenideElement lock = $("button.cq-siteadmin-admin-actions-lockpage-activator");
    private static SelenideElement unlock = $("button.cq-siteadmin-admin-actions-unlockpage-activator");
    private static SelenideElement copy = $("button.cq-siteadmin-admin-actions-copy-activator");
    private static SelenideElement move = $("button.cq-siteadmin-admin-actions-move-activator");
    private static SelenideElement more = $("button[coral-actionbar-more]");
    private static SelenideElement quickpublish = $("button.cq-siteadmin-admin-actions-quickpublish-activator");
    private static SelenideElement publish = $("button.cq-siteadmin-admin-actions-publish-activator");
    private static SelenideElement publishLater = $("button.cq-siteadmin-admin-actions-publishlater-activator");
    private static SelenideElement unpublish = $("button.cq-siteadmin-admin-actions-unpublish-activator");
    private static SelenideElement delete = $("button.cq-siteadmin-admin-actions-delete-activator");
    private static SelenideElement selectAll = $("button.foundation-collection-selectall");
    private static SelenideElement restore = $("button.cq-siteadmin-admin-actions-restore-activator");
    private static SelenideElement restoreVersion = $("a.cq-siteadmin-admin-restoreversion");
    private static SelenideElement targetExport = $("button.cq-xf-admin-actions-targetexport");
    private static SelenideElement targetDelete = $("button.cq-xf-admin-actions-targetdelete");
    private static SelenideElement targetUpdate = $("button.cq-xf-admin-actions-targetupdate");

    public ActionComponent<PropertiesPage> properties() {
      return new ActionComponent(properties, () -> new PropertiesPage(), true);
    }

    public ActionComponent<XFPropertiesFolder> folderProperties() {
      return new ActionComponent(folderProperties, () -> new XFPropertiesFolder(), true);
    }

    public ActionComponent<MovePageWizard> move() {
      return new ActionComponent(move, () -> new MovePageWizard(), true);
    }

    public ActionComponent<Dialog> targetExport() {
      return new ActionComponent(targetExport, () -> new Dialog(), false);
    }

    public ActionComponent<Dialog> targetDelete() {
      return new ActionComponent(targetDelete, () -> new Dialog(), false);
    }

    public ActionComponent<Dialog> targetUpdate() {
      return new ActionComponent(targetUpdate, () -> new Dialog(), false);
    }
  }
}