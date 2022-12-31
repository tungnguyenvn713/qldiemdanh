package com.nor.qldiemdanh.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.nor.qldiemdanh.AppContext;

public class MFloatingButton extends FloatingActionButton {
    public MFloatingButton(Context context) {
        super(context);
        init();
    }

    public MFloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MFloatingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("RestrictedApi")
    private void init() {
        if (!AppContext.getInstance().isAdmin){
            setVisibility(GONE);
        }
    }
}
