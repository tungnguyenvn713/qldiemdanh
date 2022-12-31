package com.nor.qldiemdanh.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.Result;
import com.nor.qldiemdanh.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeReader extends ZXingScannerView implements ZXingScannerView.ResultHandler {
    private final int TIME_VIBRATE = 1000;
    private Vibrator vibrator;
    private OnScanResultListener listener;

    public QRCodeReader(Context context) {
        super(context);
        init();
    }

    public QRCodeReader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        setBorderColor(Color.WHITE);
        setBorderLineLength((int) getContext().getResources().getDimension(R.dimen.dp_24));
        setBorderStrokeWidth((int) getContext().getResources().getDimension(R.dimen.dp_4));
        setLaserEnabled(false);
        setIsBorderCornerRounded(false);
        setSquareViewFinder(true);
        setResultHandler(this);
        setAutoFocus(true);
    }

    @Override
    public void handleResult(Result result) {
        stopCamera();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createOneShot(TIME_VIBRATE, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect);
        } else {
            vibrator.vibrate(TIME_VIBRATE);
        }
        if (listener != null) {
            listener.onScanResult(result.getText());
        }
    }

    public void setListener(OnScanResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE){
            startCamera();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopCamera();
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        if (isVisible) {
            startCamera();
        } else {
            stopCamera();
        }
    }

    public interface OnScanResultListener {
        void onScanResult(String result);
    }
}
