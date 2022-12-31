package com.nor.qldiemdanh.ui.schedule.checkin;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nor.qldiemdanh.databinding.ItemCheckInDetailBinding;
import com.nor.qldiemdanh.databinding.ItemCheckInHeaderBinding;
import com.nor.qldiemdanh.databinding.ItemStudentCheckinBinding;
import com.nor.qldiemdanh.model.CheckInList;
import com.nor.qldiemdanh.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CheckInHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<CheckInList> checkInList = new ArrayList<>();
    private Student student;

    public CheckInHeaderAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DayViewHolder holder = new DayViewHolder(ItemCheckInHeaderBinding.inflate(inflater));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DayViewHolder dayViewHolder = (DayViewHolder) viewHolder;
        dayViewHolder.bindData(i - 1);
    }

    @Override
    public int getItemCount() {
        return checkInList.size() + 2;
    }

    public void setData(List<CheckInList> data) {
        this.checkInList = data;
        notifyDataSetChanged();
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        ItemCheckInHeaderBinding binding;

        public DayViewHolder(@NonNull ItemCheckInHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindData(int position) {
            if (position == -1) {
                binding.setIsStudent(true);
                binding.setValue("Sinh Viên");
            } else if (position < checkInList.size()) {
                binding.setValue(checkInList.get(position).getDate());
            } else {
                binding.setValue("Tổng");
            }
        }
    }
}
