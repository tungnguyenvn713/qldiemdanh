package com.nor.qldiemdanh.views;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class MEditText extends AppCompatEditText {
    public MEditText(Context context) {
        super(context);
        init();
    }

    public MEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLines(1);
    }
}
