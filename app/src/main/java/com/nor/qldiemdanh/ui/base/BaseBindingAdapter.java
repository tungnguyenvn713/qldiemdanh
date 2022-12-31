package com.nor.qldiemdanh.ui.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.nor.qldiemdanh.common.PopupMenuUtils;

import java.util.List;

public class BaseBindingAdapter<T> extends RecyclerView.Adapter<BaseBindingAdapter.ViewHolder> {
    private List<T> data;
    private LayoutInflater inflater;
    private @LayoutRes
    int layoutRes;
    private PopupMenuUtils.ItemPopupClickListener listener;

    public BaseBindingAdapter(final Context context, int layoutRes) {
        this.layoutRes = layoutRes;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    public void setListener(PopupMenuUtils.ItemPopupClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseBindingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(inflater, layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindingAdapter.ViewHolder holder, final int position) {
        T t = data.get(position);
        holder.binding.setVariable(BR.item, t);
        holder.binding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    PopupMenuUtils.getPopupMenu(v, listener, position);
                }
            }
        });
        decodeView(holder, position);
    }

    protected void decodeView(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
