package com.nor.qldiemdanh.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nor.qldiemdanh.AppContext;
import com.nor.qldiemdanh.AppViewModel;
import com.nor.qldiemdanh.AppViewModelFactory;
import com.nor.qldiemdanh.common.Const;

public abstract class BaseBindingFragment<BD extends ViewDataBinding, ACT extends BaseBindingActivity> extends Fragment {
    protected BD binding;
    protected FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected DatabaseReference reference;
    protected AppContext appContext;
    protected AppViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, AppViewModelFactory.getInstance()).get(AppViewModel.class);
        appContext = (AppContext) getContext().getApplicationContext();
        reference = database.getReference(Const.DB_ROOT);
    }

    protected ACT getParentActivity() {
        return (ACT) getActivity();
    }

    protected abstract int getLayoutResId();

    public String getTitle(){
        return "";
    }
}
