package com.nor.qldiemdanh.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.google.gson.Gson;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeRender extends AppCompatImageView {
    public QRCodeRender(Context context) {
        super(context);
    }

    public QRCodeRender(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QRCodeRender(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void generate(Object o) {
        String content = new Gson().toJson(o);
        Bitmap bitmap = QRCodeHelper.newInstance(getContext())
                .setContent(content)
                .setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
                .setMargin(2)
                .getQRCOde();
        setImageBitmap(bitmap);
    }
}
