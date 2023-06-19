package com.mietrix.restu_digital_quran.reader_managers;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import static com.mietrix.restu_digital_quran.reader_managers.ReaderParams.READER_READ_TYPE_CHAPTER;
import static com.mietrix.restu_digital_quran.reader_managers.ReaderParams.READER_READ_TYPE_JUZ;
import static com.mietrix.restu_digital_quran.reader_managers.ReaderParams.READER_READ_TYPE_VERSES;

import com.mietrix.restu_digital_quran.activities.ActivityReader;
import com.mietrix.restu_digital_quran.activities.ReaderPossessingActivity;
import com.mietrix.restu_digital_quran.components.quran.QuranMeta;
import com.mietrix.restu_digital_quran.components.quran.subcomponents.Chapter;
import com.mietrix.restu_digital_quran.components.quran.subcomponents.Footnote;
import com.mietrix.restu_digital_quran.components.quran.subcomponents.Verse;
import com.mietrix.restu_digital_quran.components.reader.QuranPageSectionModel;
import com.mietrix.restu_digital_quran.interfaceUtils.BookmarkCallbacks;
import com.mietrix.restu_digital_quran.interfaceUtils.Destroyable;
import com.mietrix.restu_digital_quran.utils.Logger;
import com.mietrix.restu_digital_quran.utils.quran.QuranUtils;
import com.mietrix.restu_digital_quran.utils.reader.factory.ReaderFactory;
import com.mietrix.restu_digital_quran.utils.thread.runner.RunnableTaskRunner;
import com.mietrix.restu_digital_quran.views.reader.dialogs.FootnotePresenter;
import com.mietrix.restu_digital_quran.views.reader.dialogs.PageVerseDialog;
import com.mietrix.restu_digital_quran.views.reader.dialogs.QuickReference;
import com.mietrix.restu_digital_quran.views.reader.dialogs.VerseOptionsDialog;
import com.mietrix.restu_digital_quran.widgets.dialog.loader.PeaceProgressDialog;

import java.util.Set;

import kotlin.Pair;

public class ActionController implements Destroyable {
    private final ReaderPossessingActivity mActivity;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final RunnableTaskRunner taskRunner = new RunnableTaskRunner(handler);
    private final VerseOptionsDialog mVOD = new VerseOptionsDialog();
    private final FootnotePresenter mFootnotePresenter = new FootnotePresenter();
    private final QuickReference mQuickReference = new QuickReference();
    private final PageVerseDialog mPageVerseDialog = new PageVerseDialog();
    private final PeaceProgressDialog mProgressDialog;

    public ActionController(ReaderPossessingActivity readerPossessingActivity) {
        mActivity = readerPossessingActivity;

        mProgressDialog = new PeaceProgressDialog(mActivity);
        mProgressDialog.setDimAmount(0);
        mProgressDialog.setElevation(0);
    }

    public void showFootnote(Verse verse, Footnote footnote, boolean isUrduSlug) {
        mFootnotePresenter.present(mActivity, verse, footnote, isUrduSlug);
    }

    public void showFootnotes(Verse verse) {
        mFootnotePresenter.present(mActivity, verse);
    }

    public void openVerseOptionDialog(Verse verse, @Nullable BookmarkCallbacks bookmarkCallbacks) {
        mVOD.open(mActivity, verse, bookmarkCallbacks);
    }

    public void openShareDialog(int chapterNo, int verseNo) {
        mVOD.openShareDialog(mActivity, chapterNo, verseNo);
    }

    public void dismissShareDialog() {
        mVOD.dismissShareDialog();
    }

    public void showVerseReference(Set<String> translSlugs, int chapterNo, String verses) {
        try {
            mQuickReference.show(mActivity, translSlugs, chapterNo, verses);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.reportError(e);
        }
    }

    public void showReferenceSingleVerseOrRange(
        Set<String> translSlugs,
        int chapterNo,
        Pair<Integer, Integer> verseRange
    ) {
        mQuickReference.showSingleVerseOrRange(mActivity, translSlugs, chapterNo, verseRange);
    }

    public void openVerseReference(int chapterNo, Pair<Integer, Integer> verseRange) {
        if (mActivity instanceof ActivityReader) {
            openVerseReferenceWithinReader((ActivityReader) mActivity, chapterNo, verseRange);
        } else {
            ReaderFactory.startVerseRange(mActivity, chapterNo, verseRange);
        }

        closeDialogs(true);
    }

    private void openVerseReferenceWithinReader(ActivityReader activity, int chapterNo, Pair<Integer, Integer> verseRange) {
        ReaderParams readerParams = activity.mReaderParams;
        if (!QuranMeta.isChapterValid(chapterNo) || verseRange == null) {
            return;
        }

        Chapter chapter = mActivity.mQuranRef.get().getChapter(chapterNo);

        final boolean initNewRange;
        boolean isReferencedVerseSingle = QuranUtils.doesRangeDenoteSingle(verseRange);

        if (isReferencedVerseSingle) {
            if (readerParams.readType == READER_READ_TYPE_JUZ) {
                initNewRange = !mActivity.mQuranMetaRef.get().isVerseValid4Juz(readerParams.currJuzNo, chapterNo,
                    verseRange.getFirst());
            } else if (readerParams.readType == READER_READ_TYPE_CHAPTER || readerParams.isSingleVerse()) {
                initNewRange = !chapter.equals(readerParams.currChapter);
            } else if (readerParams.readType == READER_READ_TYPE_VERSES) {
                initNewRange = !QuranUtils.isVerseInRange(verseRange.getFirst(), readerParams.verseRange);
            } else {
                initNewRange = true;
            }
        } else {
            initNewRange = true;
        }

        if (initNewRange) {
            activity.initVerseRange(chapter, verseRange);
        } else {
            activity.mNavigator.jumpToVerse(chapterNo, verseRange.getFirst(), false);
        }
    }

    public void showPageVerseDialog(QuranPageSectionModel section, Verse verse) {
        if (!(mActivity instanceof ActivityReader)) return;

        mPageVerseDialog.show((ActivityReader) mActivity, verse);
    }

    public void onVerseRecite(int chapterNo, int verseNo, boolean isReciting) {
        mVOD.onVerseRecite(chapterNo, verseNo, isReciting);
        mPageVerseDialog.onVerseRecite(chapterNo, verseNo, isReciting);
    }

    public void showLoader() {
        mProgressDialog.show();
    }

    public void dismissLoader() {
        mProgressDialog.dismiss();
    }

    public void closeDialogs(boolean closeForReal) {
        mProgressDialog.dismiss();

        if (closeForReal || mActivity.isFinishing()) {
            mQuickReference.dismiss();
            mVOD.dismiss();
            mVOD.dismissShareDialog();
            mFootnotePresenter.dismiss();
            mPageVerseDialog.dismiss();
        }
    }

    @Override
    public void destroy() {
        taskRunner.cancel();

        closeDialogs(false);
    }
}
