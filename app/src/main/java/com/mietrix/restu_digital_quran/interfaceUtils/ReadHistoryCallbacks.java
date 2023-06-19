/*
 * (c) Faisal Khan. Created on 20/11/2021.
 */

package com.mietrix.restu_digital_quran.interfaceUtils;

import com.mietrix.restu_digital_quran.components.readHistory.ReadHistoryModel;

public interface ReadHistoryCallbacks {
    void onReadHistoryRemoved(ReadHistoryModel model);

    void onReadHistoryAdded(ReadHistoryModel model);
}
