package com.nor.qldiemdanh.ui.teacher;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.Const;
import com.nor.qldiemdanh.common.DialogUtils;
import com.nor.qldiemdanh.common.PopupMenuUtils;
import com.nor.qldiemdanh.databinding.UiTeacherBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Teacher;
import com.nor.qldiemdanh.ui.base.BaseBindingAdapter;
import com.nor.qldiemdanh.ui.base.BaseBindingFragment;
import com.nor.qldiemdanh.ui.main.MainActivity;

import java.util.List;

public class TeacherFragment extends BaseBindingFragment<UiTeacherBinding, MainActivity> implements View.OnClickListener, PopupMenuUtils.ItemPopupClickListener {
    private BaseBindingAdapter<Teacher> adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.ui_teacher;
    }

    @Override
    public String getTitle() {
        return getContext().getResources().getString(R.string.teacher);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.btnAdd.setOnClickListener(this);
        adapter = new BaseBindingAdapter<>(getContext(), R.layout.item_teacher);
        adapter.setListener(this);
        binding.lvTeacher.setAdapter(adapter);
        reference = reference.child(Const.ROOT_TEACHER);
        viewModel.getTeachers().observe(this, new Observer<List<Teacher>>() {
            @Override
            public void onChanged(@Nullable List<Teacher> teachers) {
                adapter.setData(teachers);
            }
        });
    }

    @Override
    public void onClick(View v) {
        startTeacherActivity(null);
    }

    private void startTeacherActivity(Teacher teacher) {
        Intent intent = new Intent(getContext(), TeacherActivity.class);
        intent.putExtra(Entity.class.getName(), teacher);
        startActivity(intent);
    }

    @Override
    public void onPopupEdit(int position) {
        startTeacherActivity(viewModel.getTeachers().getValue().get(position));
    }

    @Override
    public void onPopupDelete(int position) {
        DialogUtils.showDialogLoading(getContext());
        reference.child(viewModel.getTeachers().getValue().get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                DialogUtils.dismiss();
            }
        });
    }
}
