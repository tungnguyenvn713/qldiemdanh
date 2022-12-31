package com.nor.qldiemdanh.ui.schedule;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.StringUtils;
import com.nor.qldiemdanh.databinding.ActivityScheduleBinding;
import com.nor.qldiemdanh.databinding.ActivitySubjectBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Room;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.model.Subject;
import com.nor.qldiemdanh.model.Teacher;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;

import java.util.List;

public class ScheduleActivity extends BaseBindingActivityChild<ActivityScheduleBinding> implements View.OnClickListener {
    private Schedule schedule;
    private ArrayAdapter<Subject> adapterSubject;
    private ArrayAdapter<Teacher> adapterTeacher;
    private ArrayAdapter<Room> adapterRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btnAdd.setOnClickListener(this);
        schedule = (Schedule) getIntent().getSerializableExtra(Entity.class.getName());
        viewModel.getSubjects().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable List<Subject> subjects) {
                adapterSubject = new ArrayAdapter<>(ScheduleActivity.this, android.R.layout.simple_list_item_1, subjects);
                binding.spSubject.setAdapter(adapterSubject);
            }
        });

        viewModel.getTeachers().observe(this, new Observer<List<Teacher>>() {
            @Override
            public void onChanged(@Nullable List<Teacher> teachers) {
                adapterTeacher = new ArrayAdapter<>(ScheduleActivity.this, android.R.layout.simple_list_item_1, teachers);
                binding.spTeacher.setAdapter(adapterTeacher);
            }
        });

        viewModel.getRooms().observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(@Nullable List<Room> rooms) {
                adapterRoom = new ArrayAdapter<>(ScheduleActivity.this, android.R.layout.simple_list_item_1, rooms);
                binding.spRoom.setAdapter(adapterRoom);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_schedule;
    }

    @Override
    public void onClick(View v) {
        if (binding.spTeacher.getSelectedItem() == null) {
            Toast.makeText(this, R.string.teacher_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.spSubject.getSelectedItem() == null) {
            Toast.makeText(this, R.string.subject_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.spRoom.getSelectedItem() == null) {
            Toast.makeText(this, R.string.room_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }
        if (schedule == null) {
            schedule = new Schedule();
        }
        schedule.setIdRoom(((Room) binding.spRoom.getSelectedItem()).getId());
        schedule.setIdSubject(((Subject) binding.spSubject.getSelectedItem()).getId());
        schedule.setIdTeacher(((Teacher) binding.spTeacher.getSelectedItem()).getId());
        putData(schedule);
    }
}
