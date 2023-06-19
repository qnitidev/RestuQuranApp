package com.mietrix.restu_digital_quran.views.reader.chapterSpinner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mietrix.restu_digital_quran.R;
import com.mietrix.restu_digital_quran.components.quran.subcomponents.Chapter;
import com.mietrix.restu_digital_quran.views.reader.ChapterIcon;
import com.mietrix.restu_digital_quran.views.reader.spinner.ReaderSpinner;
import com.mietrix.restu_digital_quran.views.reader.spinner.ReaderSpinnerItem;

import java.util.regex.Pattern;

public class ChapterSpinner extends ReaderSpinner {
    private ChapterIcon mChapterIconView;

    public ChapterSpinner(@NonNull Context context) {
        super(context);
    }

    public ChapterSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChapterSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean search(ReaderSpinnerItem item, Pattern pattern) {
        ChapterSpinnerItem chapterItem = (ChapterSpinnerItem) item;
        Chapter chapter = chapterItem.getChapter();
        return pattern.matcher(chapter.getChapterNumber() + chapter.getTags()).find();
    }

    @Override
    protected String getPopupTitle() {
        return getContext().getString(R.string.strTitleReaderChapters);
    }

    @Override
    protected String getPopupSearchHint() {
        return getContext().getString(R.string.strHintSearchChapter);
    }

    @Override
    protected void setSpinnerTextInternal(TextView textView, ReaderSpinnerItem item) {
        super.setSpinnerTextInternal(textView, item);
        if (mChapterIconView != null) {
            mChapterIconView.setChapterNumber(((ChapterSpinnerItem) item).getChapter().getChapterNumber());
        }
    }

    public void setChapterIconView(ChapterIcon chapterIconView) {
        mChapterIconView = chapterIconView;
    }
}
