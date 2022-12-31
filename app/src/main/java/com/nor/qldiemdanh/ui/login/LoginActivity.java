package com.nor.qldiemdanh.ui.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.nor.qldiemdanh.AppViewModel;
import com.nor.qldiemdanh.AppViewModelFactory;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.DialogUtils;
import com.nor.qldiemdanh.common.StringUtils;
import com.nor.qldiemdanh.databinding.ActivityLoginBinding;
import com.nor.qldiemdanh.model.User;
import com.nor.qldiemdanh.ui.base.BaseBindingActivity;
import com.nor.qldiemdanh.ui.main.MainActivity;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseBindingActivity<ActivityLoginBinding> implements View.OnClickListener, Observer<User> {
    private AppViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, AppViewModelFactory.getInstance()).get(AppViewModel.class);
        viewModel.getUser().observe(this, this);
        binding.btnLogin.setVisible();
        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        if (StringUtils.isEmpty(binding.edtPassword, binding.edtUserName)) {
            return;
        }
        DialogUtils.showDialogLoading(this);
        viewModel.login(binding.edtUserName.getText().toString(), binding.edtPassword.getText().toString());
    }

    @Override
    public void onChanged(@Nullable User entity) {
        DialogUtils.dismiss();
        if (entity == null) {
            Snackbar.make(binding.btnLogin, R.string.login_fail, Snackbar.LENGTH_LONG).show();
            return;
        }
        Toasty.success(this,"Đăng nhập thành công",Toasty.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
