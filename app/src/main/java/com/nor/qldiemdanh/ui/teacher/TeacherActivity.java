package com.nor.qldiemdanh.ui.teacher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.StringUtils;
import com.nor.qldiemdanh.databinding.ActivityTeacherBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Teacher;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;
import com.nor.qldiemdanh.ui.base.Binding;

public class TeacherActivity extends BaseBindingActivityChild<ActivityTeacherBinding> implements View.OnClickListener {
    private Teacher teacher;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_teacher;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btnAdd.setOnClickListener(this);
        teacher = (Teacher) getIntent().getSerializableExtra(Entity.class.getName());
        if (teacher != null) {
            Binding.setImageFromUrl(binding.imAvatar, teacher.getImg());
            binding.edtName.setText(teacher.getName());
            binding.edtAddress.setText(teacher.getAddress());
            binding.edtDegree.setText(teacher.getDegree());
            binding.edtDob.setDate(teacher.getDob());
            binding.edtEmail.setText(teacher.getEmail());
            binding.edtPhone.setText(teacher.getPhone());
        }
    }

    @Override
    public void onClick(View v) {
        if (teacher == null) {
            teacher = new Teacher();
        }
        if (StringUtils.isEmpty(binding.edtAddress, binding.edtDegree, binding.edtDob,
                binding.edtEmail, binding.edtName, binding.edtPhone)) {
            return;
        }
        if (!StringUtils.isEmail(binding.edtEmail)){
            return;
        }
        teacher.setAddress(binding.edtAddress.getText().toString());
        teacher.setDegree(binding.edtDegree.getText().toString());
        teacher.setDob(binding.edtDob.toString());
        teacher.setEmail(binding.edtEmail.getText().toString());
        teacher.setName(binding.edtName.getText().toString());
        teacher.setPhone(binding.edtPhone.getText().toString());
        putData(teacher);
    }
}
