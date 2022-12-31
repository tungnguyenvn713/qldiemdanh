package com.nor.qldiemdanh.ui.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.databinding.ActivityScheduleDetailBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.model.ScheduleDetail;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;

public class ScheduleDetailActivity extends BaseBindingActivityChild<ActivityScheduleDetailBinding> implements View.OnClickListener {
    public static final String EXTRA_INDEX = "extra_index";
    private Schedule schedule;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schedule = (Schedule) getIntent().getSerializableExtra(Entity.class.getName());
        index = getIntent().getIntExtra(EXTRA_INDEX, -1);
        if (index >= 0) {
            binding.spDay.setSelection(schedule.getDetails().get(index).getDay());
            if (schedule.getDetails().get(index).getLessonFrom()>0) {
                binding.lessonFrom.setSelection(schedule.getDetails().get(index).getLessonFrom() - 1);
                binding.lessonTo.setSelection(schedule.getDetails().get(index).getLessonTo() - 1);
            }
        }
        binding.btnAdd.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_schedule_detail;
    }

    @Override
    public void onClick(View view) {
        int from = binding.lessonFrom.getSelectedItemPosition() + 1;
        int to = binding.lessonTo.getSelectedItemPosition() + 1;
        if (from >= to) {
            Toast.makeText(this, R.string.lession_validate, Toast.LENGTH_SHORT).show();
            return;
        }
        if (index == -1) {
            schedule.getDetails().add(new ScheduleDetail());
            index = schedule.getDetails().size() - 1;
        }
        schedule.getDetails().get(index).setDay(binding.spDay.getSelectedItemPosition());
        schedule.getDetails().get(index).setLessonFrom(from);
        schedule.getDetails().get(index).setLessonTo(to);
        putData(schedule);
    }
}
