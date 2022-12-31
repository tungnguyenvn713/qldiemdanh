package com.nor.qldiemdanh.ui.schedule.checkin;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.nor.qldiemdanh.AppContext;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.databinding.ActivityCheckInBinding;
import com.nor.qldiemdanh.model.CheckInList;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.model.Student;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;
import com.nor.qldiemdanh.ui.base.BaseBindingAdapter;

import java.util.List;

public class CheckInActivity extends BaseBindingActivityChild<ActivityCheckInBinding> implements Observer<List<Student>> {
    private CheckInAdapter adapter;
    private CheckInHeaderAdapter headerAdapter;
    private BaseBindingAdapter<Student> adapterStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Schedule schedule = (Schedule) getIntent().getSerializableExtra(Entity.class.getName());
        adapter = new CheckInAdapter(this);
        headerAdapter = new CheckInHeaderAdapter(this);
        adapterStudent = new BaseBindingAdapter<>(this, R.layout.item_student);
        binding.lvStudentInClass.setAdapter(adapterStudent);
        if (!AppContext.getInstance().isStudent) {
            binding.lvStudentInClass.setVisibility(View.GONE);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return true;
            }
        };
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.lvHeader.setLayoutManager(manager);
        binding.lvCheckIn.setAdapter(adapter);
        binding.lvHeader.setAdapter(headerAdapter);
        viewModel.getCheckIn(schedule.getId());
        viewModel.getCheckInList().observe(this, new Observer<List<CheckInList>>() {
            @Override
            public void onChanged(@Nullable List<CheckInList> checkInLists) {
                adapter.setData(checkInLists);
                headerAdapter.setData(checkInLists);
            }
        });
        setTitle(schedule.getRoomName() + " - " + schedule.getSubject().getName());
        viewModel.studentRegistered(schedule.getId());
        viewModel.getStudentsRegistered().observe(this, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_check_in;
    }

    @Override
    public void onChanged(@Nullable List<Student> students) {
        binding.tvTotalSv.setText("Tổng sinh viên: " + students.size());
        if (AppContext.getInstance().isStudent) {
            for (Student student : students) {
                if (student.getId().equals(viewModel.getUser().getValue().getId())) {
                    adapter.setStudent(student);
                    break;
                }
            }
            adapterStudent.setData(students);
        } else {
            adapter.setStudents(students);
        }
    }
}
