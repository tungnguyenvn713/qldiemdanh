package com.nor.qldiemdanh.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.nor.qldiemdanh.R;

public class MButton extends AppCompatButton {
    public MButton(Context context) {
        super(context);
        init();
    }

    public MButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.color.colorPrimary);
        setTextColor(Color.WHITE);
//        if (!AppContext.getInstance().isAdmin){
//            setVisibility(GONE);
//        }
    }

    public void setVisible(){
        setVisibility(VISIBLE);
    }
}
