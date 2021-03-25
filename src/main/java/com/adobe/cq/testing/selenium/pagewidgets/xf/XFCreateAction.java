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

package com.adobe.cq.testing.selenium.pagewidgets.xf;

import com.adobe.cq.testing.selenium.pageobject.cq.xf.XFCreateWizard;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralPopOver;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;

import static org.openqa.selenium.By.className;

public class XFCreateAction extends BaseComponent {

    public static final String XF_CREATE_SELECTOR = "button.granite-collection-create.foundation-toggleable-control";

    private static final String XF_CREATE_WIZARD = "cq-experiencefragments-admin-create-xf";
    private static final String XF_LANGUAGE_COPY = "cq-experiencefragments-admin-create-launch";
    private static final String XF_FOLDER = "cq-siteadmin-admin-createfolder";
    private static final String XF_LIVE_COPY = "cq-experiencefragments-admin-create-livecopy";
    private static final String XF_CREATE_VARIANT = "cq-experiencefragments-admin-create-variant";
    private static final String XF_CREATE_VARIANT_LIVECOPY = "cq-experiencefragments-admin-create-livecopy-variation";
    private static final String XF_LAUNCH = "cq-experiencefragments-admin-create-launch";

    public XFCreateAction() {
        super(XF_CREATE_SELECTOR);
    }

    public ActionComponent<XFCreateWizard> xf() {
        return new ActionComponent<XFCreateWizard>(CoralPopOver.firstOpened().element().$(className(XF_CREATE_WIZARD)), () -> new XFCreateWizard(), true);
    }

    public ActionComponent<Dialog> folder() {
        return new ActionComponent<Dialog>(CoralPopOver.firstOpened().element().$(className(XF_FOLDER)), () -> new Dialog(), false);
    }

    public ActionComponent<Dialog> livecopy() {
        return new ActionComponent<Dialog>(CoralPopOver.firstOpened().element().$(className(XF_LIVE_COPY)), () -> new Dialog(), false);
    }

    public ActionComponent<Dialog> languageCopy() {
        return new ActionComponent<Dialog>(CoralPopOver.firstOpened().element().$(className(XF_LANGUAGE_COPY)), () -> new Dialog(), false);
    }

    public ActionComponent<Dialog> launch() {
        return new ActionComponent<Dialog>(CoralPopOver.firstOpened().element().$(className(XF_LAUNCH)), () -> new Dialog(), false);
    }

    public ActionComponent<XFCreateWizard> variant() {
        return new ActionComponent<XFCreateWizard>(CoralPopOver.firstOpened().element().$(className(XF_CREATE_VARIANT)), () -> new XFCreateWizard(), false);
    }

    public ActionComponent<Dialog> variantLivecopy() {
        return new ActionComponent<Dialog>(CoralPopOver.firstOpened().element().$(className(XF_CREATE_VARIANT_LIVECOPY)), () -> new Dialog(), false);
    }
}
