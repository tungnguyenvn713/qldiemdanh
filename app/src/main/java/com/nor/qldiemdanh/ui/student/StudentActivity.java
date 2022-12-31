package com.nor.qldiemdanh.ui.student;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;

import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.Const;
import com.nor.qldiemdanh.common.StringUtils;
import com.nor.qldiemdanh.databinding.ActivityStudentBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Student;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;
import com.nor.qldiemdanh.ui.base.Binding;

import java.util.List;

public class StudentActivity extends BaseBindingActivityChild<ActivityStudentBinding> implements View.OnClickListener {
    private Student student;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_student;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference.child(Const.DB_ROOT);
        binding.btnAdd.setOnClickListener(this);
        student = (Student) getIntent().getSerializableExtra(Entity.class.getName());
        if (student != null) {
            Binding.setImageFromUrl(binding.imAvatar, student.getImg());
            binding.edtName.setText(student.getName());
            binding.edtAddress.setText(student.getAddress());
            binding.spSex.setSelection(student.getSex().equalsIgnoreCase("Name") ? 0 : 1);
            binding.edtDob.setDate(student.getDob());
            binding.edtEmail.setText(student.getEmail());
            binding.edtPhone.setText(student.getPhone());
        }
        binding.setItem(student);
    }

    @Override
    public void onClick(View v) {
        if (student == null) {
            student = new Student();
        }
        if (StringUtils.isEmpty(binding.edtAddress, binding.edtDob,
                binding.edtEmail, binding.edtName, binding.edtPhone)) {
            return;
        }
        if (!StringUtils.isEmail(binding.edtEmail)) {
            return;
        }
        student.setAddress(binding.edtAddress.getText().toString());
        student.setSex(binding.spSex.getSelectedItem().toString());
        student.setDob(binding.edtDob.toString());
        student.setEmail(binding.edtEmail.getText().toString());
        student.setName(binding.edtName.getText().toString());
        student.setPhone(binding.edtPhone.getText().toString());
        putData(student);
    }
}
