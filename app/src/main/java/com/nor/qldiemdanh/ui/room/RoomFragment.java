package com.nor.qldiemdanh.ui.room;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.Const;
import com.nor.qldiemdanh.common.DialogUtils;
import com.nor.qldiemdanh.common.PopupMenuUtils;
import com.nor.qldiemdanh.databinding.UiRoomBinding;
import com.nor.qldiemdanh.databinding.UiScheduleBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Room;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.ui.base.BaseBindingAdapter;
import com.nor.qldiemdanh.ui.base.BaseBindingFragment;
import com.nor.qldiemdanh.ui.main.MainActivity;
import com.nor.qldiemdanh.ui.schedule.ScheduleActivity;

import java.util.List;

public class RoomFragment extends BaseBindingFragment<UiRoomBinding, MainActivity> implements PopupMenuUtils.ItemPopupClickListener, View.OnClickListener {
    private BaseBindingAdapter<Room> adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.ui_room;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseBindingAdapter<>(getContext(), R.layout.item_room);
        adapter.setListener(this);
        binding.lvRoom.setAdapter(adapter);
        binding.btnAdd.setOnClickListener(this);
        reference = reference.child(Const.ROOT_ROOM);
        viewModel.getRooms().observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(@Nullable List<Room> rooms) {
                adapter.setData(rooms);
            }
        });
    }

    @Override
    public void onPopupEdit(int position) {
        startRoomActivity(viewModel.getRooms().getValue().get(position));
    }

    @Override
    public void onPopupDelete(int position) {
        DialogUtils.showDialogLoading(getContext());
        reference.child(viewModel.getRooms().getValue().get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                DialogUtils.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        startRoomActivity(null);
    }

    private void startRoomActivity(Room room){
        Intent intent = new Intent(getContext(), RoomActivity.class);
        intent.putExtra(Entity.class.getName(), room);
        startActivity(intent);
    }

    @Override
    public String getTitle() {
        return getContext().getResources().getString(R.string.room);
    }
}
