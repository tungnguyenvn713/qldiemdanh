package com.nor.qldiemdanh.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nor.qldiemdanh.AppViewModel;
import com.nor.qldiemdanh.AppViewModelFactory;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.Const;
import com.nor.qldiemdanh.common.DialogUtils;
import com.nor.qldiemdanh.model.Entity;

public abstract class BaseBindingActivityChild<BD extends ViewDataBinding> extends BaseBindingActivity<BD> implements OnCompleteListener<Void>, OnFailureListener {
    protected FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected DatabaseReference reference;
    protected AppViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = ViewModelProviders.of(this, AppViewModelFactory.getInstance()).get(AppViewModel.class);
        reference = database.getReference(Const.DB_ROOT);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void putData(Entity entity) {
        DialogUtils.showDialogLoading(this);
        reference.child(entity.getRoot()).child(entity.getId()).setValue(entity)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        DialogUtils.dismiss();
        e.printStackTrace();
        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.push_fail, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        DialogUtils.dismiss();
        finish();
    }
}
