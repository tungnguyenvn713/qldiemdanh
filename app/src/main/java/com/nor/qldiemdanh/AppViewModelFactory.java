package com.nor.qldiemdanh;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AppViewModelFactory implements ViewModelProvider.Factory {
    private static AppViewModelFactory self;
    private AppViewModel appViewModel;

    private AppViewModelFactory(){
        appViewModel = new AppViewModel(AppContext.getInstance());
    }

    public static AppViewModelFactory getInstance() {
        if (self == null){
            self = new AppViewModelFactory();
        }
        return self;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) appViewModel;
    }
}
