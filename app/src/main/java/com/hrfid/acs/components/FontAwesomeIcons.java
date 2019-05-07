package com.hrfid.acs.components;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class FontAwesomeIcons extends AppCompatTextView {
    public FontAwesomeIcons(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontAwesomeIcons(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesomeIcons(Context context) {
        super(context);
        init();
    }

    private void init() {

        //Font name should not contain "/".
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fa-solid-900.ttf");
        setTypeface(tf);
    }
}
