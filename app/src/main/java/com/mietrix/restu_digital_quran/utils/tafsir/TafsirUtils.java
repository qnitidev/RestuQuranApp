package com.mietrix.restu_digital_quran.utils.tafsir;

import androidx.annotation.Nullable;

import com.mietrix.restu_digital_quran.api.models.tafsir.TafsirInfoModel;
import com.mietrix.restu_digital_quran.utils.app.AppUtils;
import com.mietrix.restu_digital_quran.utils.reader.tafsir.TafsirManager;
import com.mietrix.restu_digital_quran.utils.univ.FileUtils;

import java.util.List;
import java.util.Map;

public class TafsirUtils {
    public static final String DIR_NAME = FileUtils.createPath(AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR, "tafsirs");
    public static final String AVAILABLE_TAFSIRS_FILENAME = "available_tafsirs.json";
    public static final String KEY_TAFSIR = "key.tafsir";

    @Nullable
    public static String getTafsirName(String key) {
        if (key == null) {
            return null;
        }

        TafsirInfoModel model = TafsirManager.getModel(key);
        if (model == null) {
            return null;
        }

        return model.getName();
    }

    public static String getTafsirSlugFromKey(String key) {
        TafsirInfoModel model = TafsirManager.getModel(key);
        if (model == null) {
            return null;
        }

        return model.getSlug();
    }

    public static String getDefaultTafsirKey() {
        Map<String, List<TafsirInfoModel>> models = TafsirManager.getModels();
        if (models == null) {
            return null;
        }

        List<TafsirInfoModel> tafsirs = models.get("en");

        if (tafsirs == null || tafsirs.isEmpty()) {
            return null;
        }

        return tafsirs.get(0).getKey();
    }

    public static boolean isUrdu(String key) {
        TafsirInfoModel model = TafsirManager.getModel(key);
        if (model == null) {
            return false;
        }

        return model.getLangCode().equals("ur");
    }
}
