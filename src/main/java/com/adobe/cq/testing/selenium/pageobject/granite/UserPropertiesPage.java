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

package com.adobe.cq.testing.selenium.pageobject.granite;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

/**
 * The user properties page.
 */
public class UserPropertiesPage extends BasePage {
    // todo: This should be moved in a proper PageObject and test namespace. The naming is confusing.
    private static final String USER_PROP_ROOT = "/libs/granite/ui/content/userproperties";
    private final String userList;

    /**
     * Default constructor.
     */
    public UserPropertiesPage() {
        super("/");
        userList = USER_PROP_ROOT + "/self/form/items/wrapper/field.userlist.html";
    }

    /**
     * Query the userlist component.
     *
     * @param query - the query string.
     */
    public void queryUserList(final String query) {
        super.open(userList + "?" + query);
    }

    /**
     * Get all the found user elements.
     *
     * @return ElementsCollection of All found user elements.
     */
    public ElementsCollection userListSearchResults() {
        return $$("li.js-userpicker-item");
    }
}
