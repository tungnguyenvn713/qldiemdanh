package com.nor.qldiemdanh.ui.subject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.StringUtils;
import com.nor.qldiemdanh.databinding.ActivitySubjectBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Subject;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;

public class SubjectActivity extends BaseBindingActivityChild<ActivitySubjectBinding> implements View.OnClickListener {
    private Subject subject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btnAdd.setOnClickListener(this);
        subject = (Subject) getIntent().getSerializableExtra(Entity.class.getName());
        if (subject != null) {
            binding.edtName.setText(subject.getName());
            binding.edtLesson.setText(subject.getLesson() + "");
            binding.edtLevel.setText(subject.getLevel() + "");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_subject;
    }

    @Override
    public void onClick(View v) {
        if (StringUtils.isEmpty(binding.edtLesson, binding.edtLevel, binding.edtName)) {
            return;
        }
        if (subject == null) {
            subject = new Subject();
        }
        subject.setName(binding.edtName.getText().toString());
        subject.setLesson(Integer.parseInt(binding.edtLesson.getText().toString()));
        subject.setLevel(Float.parseFloat(binding.edtLevel.getText().toString()));
        putData(subject);
    }
}
