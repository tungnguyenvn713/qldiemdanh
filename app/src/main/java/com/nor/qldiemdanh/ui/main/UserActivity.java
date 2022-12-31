package com.nor.qldiemdanh.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.common.DialogUtils;
import com.nor.qldiemdanh.common.StringUtils;
import com.nor.qldiemdanh.databinding.ActivityUserBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.User;
import com.nor.qldiemdanh.ui.base.BaseBindingActivityChild;
import com.nor.qldiemdanh.ui.base.Binding;

public class UserActivity extends BaseBindingActivityChild<ActivityUserBinding> implements View.OnClickListener {
    private static final int REQUEST_IMG = 1;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra(Entity.class.getName());
        binding.setUser(user);
        binding.btnAdd.setOnClickListener(this);
        binding.imAvatar.setOnClickListener(this);
        binding.btnAdd.setVisible();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_avatar:
                pickImg();
                break;
            case R.id.btn_add:
                submitData();
                break;
        }
    }

    private void pickImg() {
        if (!checkPermission()) return;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_img)), REQUEST_IMG);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                return false;
            }
        }
        return true;
    }

    private void submitData() {
        if (StringUtils.isEmpty(binding.edtAddress, binding.edtDob, binding.edtName, binding.edtPassword, binding.edtPhone)) {
            return;
        }
        user.setAddress(binding.edtAddress.getText().toString());
        user.setDob(binding.edtDob.getText().toString());
        user.setName(binding.edtName.getText().toString());
        user.setPassword(binding.edtPassword.getText().toString());
        user.setPhone(binding.edtPhone.getText().toString());
        putData(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMG && resultCode == RESULT_OK) {
            DialogUtils.showDialogLoading(this);
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReference().child("image/" + user.getId());
            storageRef.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    DialogUtils.dismiss();
                    Binding.setImageFromUri(binding.imAvatar, data.getData());
                }
            })
                    .addOnFailureListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickImg();
    }
}
