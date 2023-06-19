package com.mietrix.restu_digital_quran.vh.search;

import com.mietrix.restu_digital_quran.components.search.ChapterJumpModel;
import com.mietrix.restu_digital_quran.components.search.SearchResultModelBase;
import com.mietrix.restu_digital_quran.utils.reader.factory.ReaderFactory;
import com.mietrix.restu_digital_quran.widgets.chapterCard.ChapterCard;

public class VHChapterJump extends VHSearchResultBase {
    private final ChapterCard mChapterCard;

    public VHChapterJump(ChapterCard chapterCard, boolean applyMargins) {
        super(chapterCard);
        mChapterCard = chapterCard;

        setupJumperView(chapterCard, applyMargins);
    }


    @Override
    public void bind(SearchResultModelBase parentModel, int pos) {
        ChapterJumpModel model = (ChapterJumpModel) parentModel;

        mChapterCard.setChapterNumber(model.getChapterNo());
        mChapterCard.setName(model.getName(), model.getNameTranslation());
        mChapterCard.setOnClickListener(v -> ReaderFactory.startChapter(v.getContext(), model.getChapterNo()));
    }
}
