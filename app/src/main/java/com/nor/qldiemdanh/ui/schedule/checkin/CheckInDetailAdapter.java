package com.nor.qldiemdanh.ui.schedule.checkin;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nor.qldiemdanh.databinding.ItemCheckInDetailBinding;
import com.nor.qldiemdanh.databinding.ItemStudentBinding;
import com.nor.qldiemdanh.databinding.ItemStudentCheckinBinding;
import com.nor.qldiemdanh.model.CheckIn;
import com.nor.qldiemdanh.model.CheckInList;
import com.nor.qldiemdanh.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CheckInDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<CheckInList> checkInList = new ArrayList<>();
    private Student student;

    public CheckInDetailAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewDataBinding binding;
        if (i == 0) {
            binding = ItemStudentCheckinBinding.inflate(inflater);
        } else {
            binding = ItemCheckInDetailBinding.inflate(inflater);
        }
        DayViewHolder holder = new DayViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DayViewHolder dayViewHolder = (DayViewHolder) viewHolder;
        dayViewHolder.bindData(i - 1);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return checkInList.size() + 2;
    }

    public void setData(List<CheckInList> data, Student student) {
        this.student = student;
        this.checkInList = data;
        notifyDataSetChanged();
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public DayViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private int getNumberCheckIn() {
            int number = 0;
            for (CheckInList c : checkInList) {
                if (getStatus(c.getData())) {
                    number++;
                }
            }
            return number;
        }

        private void bindData(int position) {
            if (position == -1) {
                ItemStudentCheckinBinding binding = (ItemStudentCheckinBinding) this.binding;
                binding.setItem(student);
            } else if (position < checkInList.size()) {
                ItemCheckInDetailBinding binding = (ItemCheckInDetailBinding) this.binding;
                if (getStatus(checkInList.get(position).getData())) {
                    binding.tvDay.setText("O");
                    binding.tvDay.setTextColor(Color.BLUE);
                } else {
                    binding.tvDay.setText("X");
                    binding.tvDay.setTextColor(Color.RED);
                }
            } else {
                ItemCheckInDetailBinding binding = (ItemCheckInDetailBinding) this.binding;
                binding.tvDay.setText(getNumberCheckIn() + "/" + checkInList.size());
            }
        }

        private boolean getStatus(List<CheckIn> checks) {
            for (CheckIn c : checks) {
                if (c.getIdStudent().equals(student.getId())) {
                    return true;
                }
            }
            return false;
        }
    }
}
