package com.mietrix.restu_digital_quran.vh.search;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

import com.peacedesign.android.utils.Dimen;
import com.mietrix.restu_digital_quran.R;
import com.mietrix.restu_digital_quran.components.search.SearchResultModelBase;
import com.mietrix.restu_digital_quran.components.search.VerseJumpModel;
import com.mietrix.restu_digital_quran.utils.extensions.ContextKt;
import com.mietrix.restu_digital_quran.utils.extensions.ViewPaddingKt;
import com.mietrix.restu_digital_quran.utils.reader.factory.ReaderFactory;

public class VHVerseJump extends VHSearchResultBase {
    private final AppCompatTextView mTextView;

    public VHVerseJump(AppCompatTextView textView, boolean applyMargins) {
        super(textView);
        mTextView = textView;

        ViewPaddingKt.updatePaddings(textView, Dimen.dp2px(textView.getContext(), 15),
            Dimen.dp2px(textView.getContext(), 10));
        setupJumperView(textView, applyMargins);
    }

    @Override
    public void bind(@NonNull SearchResultModelBase parentModel, int pos) {
        VerseJumpModel model = (VerseJumpModel) parentModel;

        mTextView.setText(makeName(itemView.getContext(), model.titleText, model.chapterNameText));
        mTextView.setOnClickListener(v -> ReaderFactory.startVerseRange(v.getContext(),
            model.chapterNo, model.fromVerseNo, model.toVerseNo));
    }

    private CharSequence makeName(Context ctx, String text, String subtext) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        String SANS_SERIF = "sans-serif";
        ColorStateList nameClr = ContextCompat.getColorStateList(ctx, R.color.color_reader_spinner_item_label);

        SpannableString nameSS = new SpannableString(text);
        setSpan(nameSS, new TextAppearanceSpan(SANS_SERIF, Typeface.BOLD, -1, nameClr, null));
        ssb.append(nameSS);

        if (!TextUtils.isEmpty(subtext)) {
            ColorStateList translClr = ContextKt.colorStateList(ctx, R.color.color_reader_spinner_item_label2);
            int dimen2 = ContextKt.getDimenPx(ctx, R.dimen.dmnCommonSize2);

            SpannableString translSS = new SpannableString(subtext);
            setSpan(translSS, new TextAppearanceSpan(SANS_SERIF, Typeface.NORMAL, dimen2, translClr, null));
            ssb.append("\n").append(translSS);
        }
        return ssb;
    }

    private void setSpan(SpannableString ss, Object obj) {
        ss.setSpan(obj, 0, ss.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
