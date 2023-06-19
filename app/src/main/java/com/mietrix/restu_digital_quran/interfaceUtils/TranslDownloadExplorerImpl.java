/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 4/4/2022.
 * All rights reserved.
 */

package com.mietrix.restu_digital_quran.interfaceUtils;

import android.view.View;

import com.mietrix.restu_digital_quran.adapters.transl.ADPDownloadTranslations;
import com.mietrix.restu_digital_quran.components.transls.TranslModel;

public interface TranslDownloadExplorerImpl {
    void onDownloadAttempt(ADPDownloadTranslations adapter, ADPDownloadTranslations.VHDownloadTransl vhTransl, View referencedView, TranslModel model);
}
