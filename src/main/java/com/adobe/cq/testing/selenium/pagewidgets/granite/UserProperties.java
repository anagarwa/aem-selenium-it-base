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

package com.adobe.cq.testing.selenium.pagewidgets.granite;

import com.adobe.cq.testing.selenium.pageobject.granite.LoginPage;
import com.adobe.cq.testing.selenium.pagewidgets.granite.shell.ShellMenu;
import com.adobe.cq.testing.selenium.pagewidgets.granite.shell.ShellUser;
import com.adobe.cq.testing.selenium.pagewidgets.granite.shell.ShellUserFooter;

import static com.codeborne.selenide.Selenide.Wait;

public final class UserProperties extends ShellMenu {
    /**
     * @return username.
     */
    public String getUsername() {
        return user().getUsername();
    }

    /**
     * @return LoginPage.
     */
    public LoginPage signOut() {
        ShellUser shellUser = user();
        ShellUserFooter shellUserFooter = shellUser.footer();
        shellUserFooter.signOutButton().click();
        Wait().until(webdriver -> !LoginPage.isLoggedIn());
        return new LoginPage();
    }
}
