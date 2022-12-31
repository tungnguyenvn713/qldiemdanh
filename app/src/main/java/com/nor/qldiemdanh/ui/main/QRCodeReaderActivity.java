package com.nor.qldiemdanh.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.Const;
import com.nor.qldiemdanh.common.DialogUtils;
import com.nor.qldiemdanh.databinding.ActivityReaderBinding;
import com.nor.qldiemdanh.model.ScheduleQR;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;
import com.nor.qldiemdanh.views.QRCodeReader;

public class QRCodeReaderActivity extends BaseBindingActivityChild<ActivityReaderBinding> implements QRCodeReader.OnScanResultListener, Observer<Const.CHECK_IN_STATUS> {
    private String[] PERMISSIONS = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPermission()) {
            init();
        }
        viewModel.getCheckIn().observe(this, this);
    }

    private void init() {
        binding.qrCodeReader.setListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reader;
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSIONS) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSIONS, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission()) {
            init();
        } else {
            finish();
        }
    }

    @Override
    public void onScanResult(String result) {
        try {
            DialogUtils.showDialogLoading(this);
            Gson gson = new Gson();
            ScheduleQR qr = gson.fromJson(result, ScheduleQR.class);
            viewModel.checkIn(qr.getId());
        }catch (Exception ex){
            DialogUtils.showDialogMessage(this, R.string.qr_invalid, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    binding.qrCodeReader.resumeCameraPreview(binding.qrCodeReader);
                    binding.qrCodeReader.startCamera();
                }
            });
        }
    }

    @Override
    public void onChanged(@Nullable Const.CHECK_IN_STATUS check_in_status) {
        if (check_in_status == null){
            return;
        }
        DialogUtils.dismiss();
        int message = 0;
        switch (check_in_status){
            case NOT_IN:
                message = R.string.not_in;
                break;
            case NOT_TODAY:
                message = R.string.not_today;
                break;
            case SUCCESS:
                message = R.string.check_in_success;
                break;
        }
        DialogUtils.showDialogMessage(this, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                viewModel.getCheckIn().postValue(null);
            }
        });
    }
}
