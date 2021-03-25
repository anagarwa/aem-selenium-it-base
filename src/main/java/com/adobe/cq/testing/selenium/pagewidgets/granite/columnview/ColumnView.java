/* ************************************************************************
* ADOBE CONFIDENTIAL
* ___________________
*
* Copyright 2017 Adobe
* All Rights Reserved.
*
* NOTICE: All information contained herein is, and remains
* the property of Adobe and its suppliers, if any. The intellectual
* and technical concepts contained herein are proprietary to Adobe
* and its suppliers and are protected by all applicable intellectual
* property laws, including trade secret and copyright laws.
* Dissemination of this information or reproduction of this material
* is strictly forbidden unless prior written permission is obtained
* from Adobe.
**************************************************************************/
package com.adobe.cq.testing.selenium.pagewidgets.granite.columnview;

import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;

/**
 * The column view component wrapper.
 */
public final class ColumnView extends Collection {

    /**
     * @param selector the selector to wrap this object on.
     */
    public ColumnView(final String selector) {
        super(selector);
    }

    /**
     * Returns the ColumnViewItem based on the id.
     *
     * @param  id the columnview id
     * @return the wrapper over the column item
     */
    public ColumnViewItem getItem(final String id) {
        return new ColumnViewItem(getCssSelector(), id);
    }
}
