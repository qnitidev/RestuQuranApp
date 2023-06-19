package com.mietrix.restu_digital_quran.views.reader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mietrix.restu_digital_quran.R;
import com.mietrix.restu_digital_quran.components.reader.QuranPageModel;
import com.mietrix.restu_digital_quran.databinding.LytQuranPageHeadBinding;

public class QuranPageHeadView extends FrameLayout {
    private final LytQuranPageHeadBinding mBinding;

    public QuranPageHeadView(@NonNull Context context) {
        this(context, null);
    }

    public QuranPageHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuranPageHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBinding = LytQuranPageHeadBinding.inflate(LayoutInflater.from(context));
        addView(mBinding.getRoot());

        // for the marquee to work
        mBinding.chapter.setSelected(true);
        mBinding.juz.setSelected(true);
    }

    public void initWithPageModel(QuranPageModel pageModel) {
        setPageNumber(pageModel.getPageNo());
        setJuzChapter(pageModel.getJuzNo(), pageModel.getChaptersName());
    }

    public void setPageNumber(int pageNumber) {
        mBinding.pageNo.setText(getContext().getString(R.string.strLabelPageNo, pageNumber));
    }

    private void setJuzChapter(int juzNumber, String chaptersName) {
        mBinding.chapter.setText(chaptersName);

        mBinding.juz.setText(getContext().getString(R.string.strLabelJuzNo, juzNumber));
    }
}
