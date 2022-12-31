package com.nor.qldiemdanh.ui.schedule;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.Const;
import com.nor.qldiemdanh.common.DialogUtils;
import com.nor.qldiemdanh.common.PopupMenuUtils;
import com.nor.qldiemdanh.databinding.UiScheduleBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Register;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.ui.base.BaseBindingFragment;
import com.nor.qldiemdanh.ui.main.MainActivity;
import com.nor.qldiemdanh.ui.schedule.checkin.CheckInActivity;

import java.util.List;

public class ScheduleFragment extends BaseBindingFragment<UiScheduleBinding, MainActivity> implements PopupMenuUtils.ItemPopupClickListener, View.OnClickListener, ScheduleAdapter.ScheduleDetailAddListener, OnCompleteListener<Void>, OnFailureListener, OnSuccessListener<Void> {
    private ScheduleAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.ui_schedule;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ScheduleAdapter(getContext());
        adapter.setListener(this);
        adapter.setScheduleListener(this);
        binding.lvSchedule.setAdapter(adapter);
        binding.btnAdd.setOnClickListener(this);
        reference = reference.child(Const.ROOT_SCHEDULE);
        viewModel.getSchedules().observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(@Nullable List<Schedule> schedules) {
                adapter.setData(schedules);
            }
        });
        viewModel.registerSchedule();
    }

    @Override
    public void onPopupEdit(int position) {
        startScheduleActivity(viewModel.getSchedules().getValue().get(position));
    }

    @Override
    public void onPopupDelete(int position) {
        DialogUtils.showDialogLoading(getContext());
        reference.child(viewModel.getSchedules().getValue().get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                DialogUtils.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        startScheduleActivity(null);
    }

    private void startScheduleActivity(Schedule schedule) {
        Intent intent = new Intent(getContext(), ScheduleActivity.class);
        intent.putExtra(Entity.class.getName(), schedule);
        startActivity(intent);
    }

    @Override
    public String getTitle() {
        return getContext().getResources().getString(R.string.schedule);
    }

    @Override
    public void onShowListStudent(Schedule schedule) {
        Intent intent = new Intent(getContext(), CheckInActivity.class);
        intent.putExtra(Entity.class.getName(), schedule);
        startActivity(intent);
    }

    @Override
    public void onRegister(String idSchedule, boolean isRegister) {
        DialogUtils.showDialogLoading(getContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Const.DB_ROOT);
        String userId = viewModel.getUser().getValue().getId();
        Register register = new Register();
        register.setIdStudent(userId);
        reference = reference.child(Const.ROOT_REGISTER).child(idSchedule).child(userId);
        if (isRegister) {
            reference.setValue(register)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
        }else{
            reference.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    DialogUtils.dismiss();
                    viewModel.registerSchedule();
                }
            });
        }
    }

    @Override
    public void onAddDetail(Schedule schedule) {
        startScheduleDetailActivity(schedule, -1);
    }

    @Override
    public void onDeleteDetail(Schedule schedule, int position) {
        DialogUtils.showDialogLoading(getContext());
        schedule.getDetails().remove(position);
        reference.child(schedule.getId()).setValue(schedule)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
    }

    @Override
    public void onEditDetail(Schedule schedule, int position) {
        startScheduleDetailActivity(schedule, position);
    }

    private void startScheduleDetailActivity(Schedule schedule, int index) {
        Intent intent = new Intent(getContext(), ScheduleDetailActivity.class);
        intent.putExtra(Entity.class.getName(), schedule);
        intent.putExtra(ScheduleDetailActivity.EXTRA_INDEX, index);
        startActivity(intent);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        DialogUtils.dismiss();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        DialogUtils.dismiss();
    }

    @Override
    public void onSuccess(Void aVoid) {
        DialogUtils.dismiss();
        viewModel.registerSchedule();
    }
}
