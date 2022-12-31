package com.nor.qldiemdanh.ui.subject;

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
import com.nor.qldiemdanh.databinding.UiSubjectBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Subject;
import com.nor.qldiemdanh.ui.base.BaseBindingAdapter;
import com.nor.qldiemdanh.ui.base.BaseBindingFragment;
import com.nor.qldiemdanh.ui.main.MainActivity;

import java.util.List;

import static java.security.AccessController.getContext;

public class SubjectFragment extends BaseBindingFragment<UiSubjectBinding, MainActivity> implements PopupMenuUtils.ItemPopupClickListener, View.OnClickListener {
    private BaseBindingAdapter<Subject> adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.ui_subject;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseBindingAdapter<>(getContext(), R.layout.item_subject);
        adapter.setListener(this);
        binding.lvSubject.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.lvSubject.setAdapter(adapter);
        binding.btnAdd.setOnClickListener(this);
        reference = reference.child(Const.ROOT_SUBJECT);
        viewModel.getSubjects().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable List<Subject> subjects) {
                adapter.setData(subjects);
            }
        });
    }

    @Override
    public void onPopupEdit(int position) {
        startSubjectActivity(viewModel.getSubjects().getValue().get(position));
    }

    @Override
    public void onPopupDelete(int position) {
        DialogUtils.showDialogLoading(getContext());
        reference.child(viewModel.getSubjects().getValue().get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                DialogUtils.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        startSubjectActivity(null);
    }

    private void startSubjectActivity(Subject subject){
        Intent intent = new Intent(getContext(), SubjectActivity.class);
        intent.putExtra(Entity.class.getName(), subject);
        startActivity(intent);
    }

    @Override
    public String getTitle() {
        return getContext().getResources().getString(R.string.subject);
    }
}
