package com.mietrix.restu_digital_quran.components.utility;

import com.mietrix.restu_digital_quran.components.ComponentBase;

public class SpinnerItem extends ComponentBase {
    private CharSequence name;

    public SpinnerItem() {
    }

    public SpinnerItem(CharSequence name) {
        this.name = name;
    }

    public CharSequence getName() {
        return name;
    }

    public SpinnerItem setName(CharSequence name) {
        this.name = name;
        return this;
    }
}
