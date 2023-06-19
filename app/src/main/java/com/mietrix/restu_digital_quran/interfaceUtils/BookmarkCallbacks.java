/*
 * (c) Faisal Khan. Created on 20/11/2021.
 */

package com.mietrix.restu_digital_quran.interfaceUtils;

import com.mietrix.restu_digital_quran.components.bookmark.BookmarkModel;

public interface BookmarkCallbacks {
    void onBookmarkRemoved(BookmarkModel model);

    default void onBookmarkAdded(BookmarkModel model) {}

    default void onBookmarkUpdated(BookmarkModel model) {}
}
