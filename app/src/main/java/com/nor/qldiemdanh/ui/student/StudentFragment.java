package com.nor.qldiemdanh.ui.student;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.Const;
import com.nor.qldiemdanh.common.DialogUtils;
import com.nor.qldiemdanh.common.PopupMenuUtils;
import com.nor.qldiemdanh.databinding.UiStudentBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Student;
import com.nor.qldiemdanh.ui.base.BaseBindingAdapter;
import com.nor.qldiemdanh.ui.base.BaseBindingFragment;
import com.nor.qldiemdanh.ui.main.MainActivity;

import java.util.List;

public class StudentFragment extends BaseBindingFragment<UiStudentBinding, MainActivity> implements View.OnClickListener, PopupMenuUtils.ItemPopupStudentClickListener, OnCompleteListener<Void>, OnFailureListener {

    private StudentAdapter<Student> adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.btnAdd.setOnClickListener(this);
        adapter = new StudentAdapter<>(getContext(), R.layout.item_student);
        adapter.setListener(this);
        binding.lvStudent.setAdapter(adapter);
        reference = reference.child(Const.ROOT_STUDENT);
        viewModel.getStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {
                adapter.setData(students);
            }
        });
    }

    @Override
    public void onClick(View v) {
        startTeacherActivity(null);
    }

    private void startTeacherActivity(Student student) {
        Intent intent = new Intent(getContext(), StudentActivity.class);
        intent.putExtra(Entity.class.getName(), student);
        startActivity(intent);
    }

    @Override
    public void onPopupEdit(int position) {
        startTeacherActivity(viewModel.getStudents().getValue().get(position));
    }

    @Override
    public void onPopupDelete(int position) {
        DialogUtils.showDialogLoading(getContext());
        reference.child(viewModel.getStudents().getValue().get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                DialogUtils.dismiss();
            }
        });
    }

    @Override
    public void onPopupReset(int position) {
        DialogUtils.showDialogLoading(getContext());
        Student student = adapter.getData().get(position);
        student.setPassword(student.getEmail());
        reference.child(student.getId()).setValue(student)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.ui_student;
    }

    @Override
    public String getTitle() {
        return getContext().getResources().getString(R.string.student);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        DialogUtils.dismiss();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        DialogUtils.dismiss();
        e.printStackTrace();
        Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), R.string.push_fail, Snackbar.LENGTH_SHORT).show();
    }
}
