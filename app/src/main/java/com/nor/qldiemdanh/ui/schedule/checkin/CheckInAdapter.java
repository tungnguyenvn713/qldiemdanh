package com.nor.qldiemdanh.ui.schedule.checkin;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nor.qldiemdanh.databinding.ItemCheckInBinding;
import com.nor.qldiemdanh.model.CheckInList;
import com.nor.qldiemdanh.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CheckInAdapter extends RecyclerView.Adapter<CheckInAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<CheckInList> data = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    public CheckInAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    public void setStudent(Student students) {
        this.students.clear();
        this.students.add(students);
        notifyDataSetChanged();
    }

    public void setData(List<CheckInList> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(ItemCheckInBinding.inflate(inflater));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(students.get(i));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemCheckInBinding binding;
        CheckInDetailAdapter adapter;
        public ViewHolder(@NonNull ItemCheckInBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext()){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            binding.lvCheckIn.setLayoutManager(manager);
            adapter = new CheckInDetailAdapter(itemView.getContext());
            binding.lvCheckIn.setAdapter(adapter);
        }

        private void bindData(Student student){
            adapter.setData(data, student);
        }
    }
}
