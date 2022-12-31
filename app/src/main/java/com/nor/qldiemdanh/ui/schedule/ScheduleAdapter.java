package com.nor.qldiemdanh.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.nor.qldiemdanh.AppContext;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.PopupMenuUtils;
import com.nor.qldiemdanh.databinding.ItemScheduleBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.model.ScheduleDetail;
import com.nor.qldiemdanh.model.User;
import com.nor.qldiemdanh.ui.base.BaseBindingAdapter;

public class ScheduleAdapter extends BaseBindingAdapter<Schedule> {
    private ScheduleDetailAddListener listener;

    public ScheduleAdapter(Context context) {
        super(context, R.layout.item_schedule);
    }

    public void setScheduleListener(ScheduleDetailAddListener listener) {
        this.listener = listener;
    }

    @Override
    protected void decodeView(ViewHolder holder, final int position) {
        final ItemScheduleBinding binding = (ItemScheduleBinding) holder.binding;
        binding.qrCodeRender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), QRCodeViewer.class);
                intent.putExtra(Entity.class.getName(), getData().get(position));
                view.getContext().startActivity(intent);
            }
        });
        binding.cardTeacher.setItem(getData().get(position).getTeacher());
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule schedule = getData().get(position);
                switch (view.getId()) {
                    case R.id.btnAdd:
                        listener.onAddDetail(schedule);
                        break;
                    case R.id.btnRegister:
                        listener.onRegister(schedule.getId(), binding.btnRegister.getText().toString().equals(view.getContext().getString(R.string.register)));
                        break;
                    case R.id.btn_student:
                        listener.onShowListStudent(schedule);
                        break;
                }
            }
        };
        binding.btnAdd.setVisibility(View.GONE);
        binding.btnStudent.setVisibility(View.GONE);
        binding.btnRegister.setVisibility(View.GONE);
        if (AppContext.getInstance().isAdmin) {
            binding.btnAdd.setVisibility(View.VISIBLE);
            binding.btnStudent.setVisibility(View.VISIBLE);
        } else if (AppContext.getInstance().isStudent) {
            if (getData().get(position).isRegistered()) {
                binding.btnStudent.setVisibility(View.VISIBLE);
            }
            binding.btnRegister.setVisibility(View.VISIBLE);
        }else{
            binding.btnStudent.setVisibility(View.VISIBLE);
        }
        binding.btnAdd.setOnClickListener(clickListener);
        binding.btnRegister.setOnClickListener(clickListener);
        binding.btnStudent.setOnClickListener(clickListener);
        BaseBindingAdapter<ScheduleDetail> adapter = new BaseBindingAdapter<ScheduleDetail>(holder.itemView.getContext(),
                R.layout.item_schedule_detail);
        adapter.setData(getData().get(position).getDetails());
        adapter.setListener(new PopupMenuUtils.ItemPopupClickListener() {
            @Override
            public void onPopupEdit(int p) {
                listener.onEditDetail(getData().get(position), p);
            }

            @Override
            public void onPopupDelete(int p) {
                listener.onDeleteDetail(getData().get(position), p);
            }
        });
        binding.lvScheduleDetail.setAdapter(adapter);
    }

    public interface ScheduleDetailAddListener {
        void onShowListStudent(Schedule schedule);

        void onRegister(String idSchedule, boolean isRegister);

        void onAddDetail(Schedule schedule);

        void onDeleteDetail(Schedule schedule, int position);

        void onEditDetail(Schedule schedule, int position);
    }
}
