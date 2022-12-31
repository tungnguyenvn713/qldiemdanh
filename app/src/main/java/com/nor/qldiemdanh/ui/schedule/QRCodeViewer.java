package com.nor.qldiemdanh.ui.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.databinding.QrCodeViewerBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.ui.base.BaseBindingActivity;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;

public class QRCodeViewer extends BaseBindingActivityChild<QrCodeViewerBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Schedule schedule = (Schedule) getIntent().getSerializableExtra(Entity.class.getName());
        setTitle(schedule.getRoomName());
        binding.setItem(schedule);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.qr_code_viewer;
    }
}
