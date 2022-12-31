package com.nor.qldiemdanh.ui.room;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.StringUtils;
import com.nor.qldiemdanh.databinding.ActivityRoomBinding;
import com.nor.qldiemdanh.databinding.ActivityScheduleBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Room;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.model.Subject;
import com.nor.qldiemdanh.model.Teacher;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;

import java.util.List;

public class RoomActivity extends BaseBindingActivityChild<ActivityRoomBinding> implements View.OnClickListener {
    private Room room;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btnAdd.setOnClickListener(this);
        room = (Room) getIntent().getSerializableExtra(Entity.class.getName());
        if (room != null) {
            binding.edtName.setText(room.getName());
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_room;
    }

    @Override
    public void onClick(View v) {
        if (StringUtils.isEmpty(binding.edtName)) {
            return;
        }
        if (room == null) {
            room = new Room();
        }
        room.setName(binding.edtName.getText().toString());
        putData(room);
    }
}
