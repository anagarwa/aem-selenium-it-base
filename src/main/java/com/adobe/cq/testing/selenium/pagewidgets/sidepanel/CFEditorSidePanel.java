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

import com.adobe.cq.testing.selenium.pageobject.cq.dam.ContentFragmentEditorPage;
import com.adobe.cq.testing.selenium.pagewidgets.cfm.CFAssociatePage;
import com.adobe.cq.testing.selenium.pagewidgets.cfm.CFMetadataPage;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.codeborne.selenide.SelenideElement;

/**
 * Component class representing the side panel in editor page.
 */
public class CFEditorSidePanel extends BaseSidePanel<CFEditorSidePanel> {

    public CFEditorSidePanel() {
        super();
    }

    public ActionComponent<ContentFragmentEditorPage> editContent() {
        boolean expectNav = !new ContentFragmentEditorPage().isOpen();
        return new ActionComponent<ContentFragmentEditorPage>(getElementForIcon("edit"), ContentFragmentEditorPage::new, expectNav);
    }

    public ActionComponent<ContentFragmentEditorPage> annotate() {
        boolean expectNav = !new ContentFragmentEditorPage().isOpen();
        return new ActionComponent<ContentFragmentEditorPage>(getElementForIcon("note"), ContentFragmentEditorPage::new, expectNav);
    }

    public ActionComponent<CFAssociatePage> associate() {
        boolean expectNav = !new CFAssociatePage().isOpen();
        SelenideElement tab = tabView.tabList().getTabByIcon("folder").element();
        return new ActionComponent<CFAssociatePage>(getElementForIcon("folder"), CFAssociatePage::new, expectNav);
    }

    public ActionComponent<CFMetadataPage> editMetadata() {
        boolean expectNav = !new CFMetadataPage().isOpen();
        return new ActionComponent<CFMetadataPage>(getElementForIcon("infoCircle"), CFMetadataPage::new, expectNav);
    }

    public ActionComponent<ContentFragmentEditorPage> structureTree() {
        boolean expectNav = !new ContentFragmentEditorPage().isOpen();
        return new ActionComponent<ContentFragmentEditorPage>(getElementForIcon("layers"),
                ContentFragmentEditorPage::new, expectNav);
    }

    private SelenideElement getElementForIcon(final String icon) {
        return tabView.tabList().getTabByIcon(icon).element();
    }
}
