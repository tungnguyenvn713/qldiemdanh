package com.nor.qldiemdanh.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nor.qldiemdanh.AppContext;
import com.nor.qldiemdanh.AppViewModel;
import com.nor.qldiemdanh.AppViewModelFactory;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.databinding.ActivityMainBinding;
import com.nor.qldiemdanh.databinding.NavHeaderMainBinding;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.ui.base.BaseBindingActivity;
import com.nor.qldiemdanh.ui.base.BaseBindingFragment;
import com.nor.qldiemdanh.ui.login.LoginActivity;
import com.nor.qldiemdanh.ui.room.RoomFragment;
import com.nor.qldiemdanh.ui.schedule.ScheduleFragment;
import com.nor.qldiemdanh.ui.student.StudentFragment;
import com.nor.qldiemdanh.ui.subject.SubjectFragment;
import com.nor.qldiemdanh.ui.teacher.TeacherFragment;

import java.util.HashMap;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final int REQUEST_USER = 1;
    private ActionBarDrawerToggle toggle;
    private int currentIdentity = -1;
    private AppViewModel viewModel;
    private HashMap<Integer, BaseBindingFragment> fragments = new HashMap<>();
    private NavHeaderMainBinding bd;

    {
        fragments.put(R.id.nav_teacher, new TeacherFragment());
        fragments.put(R.id.nav_student, new StudentFragment());
        fragments.put(R.id.nav_subject, new SubjectFragment());
        fragments.put(R.id.nav_schedule, new ScheduleFragment());
        fragments.put(R.id.nav_room, new RoomFragment());
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, AppViewModelFactory.getInstance()).get(AppViewModel.class);
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainView.toolbar, R.string.app_name, R.string.app_name);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
        initFragment();
        bd = NavHeaderMainBinding.inflate(LayoutInflater.from(this));
        bd.setUser(viewModel.getUser().getValue());
        binding.navView.addHeaderView(bd.getRoot());
        bd.getRoot().setOnClickListener(this);
        setSupportActionBar(binding.mainView.toolbar);
        if (!viewModel.getUser().getValue().isAdmin()){
            if (viewModel.getUser().getValue().isTeacher()){
                binding.navView.getMenu().getItem(2).setVisible(false);
            }
            binding.navView.getMenu().getItem(1).setVisible(false);
            binding.navView.getMenu().getItem(0).setVisible(false);
        }
        showFragment(R.id.nav_schedule);
    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int key : fragments.keySet()) {
            transaction.add(R.id.panel, fragments.get(key));
            transaction.hide(fragments.get(key));
        }
        transaction.commitAllowingStateLoss();
    }

    public void showFragment(@IdRes final int identity) {
        if (currentIdentity == identity) return;
        currentIdentity = identity;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        for (int key : fragments.keySet()) {
            transaction.hide(fragments.get(key));
        }
        transaction.show(fragments.get(identity));
        transaction.runOnCommit(new Runnable() {
            @Override
            public void run() {
                binding.mainView.toolbar.setTitle(fragments.get(identity).getTitle());
            }
        });
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (AppContext.getInstance().isStudent ) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.menu_reader) {
            Intent intent = new Intent(this, QRCodeReaderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        if (item.getItemId() == R.id.nav_log_out){
            AppContext.getInstance().reset();
            viewModel.getUser().postValue(null);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        showFragment(item.getItemId());
        return true;
    }

    @Override
    public void onClick(View v) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(Entity.class.getName(), viewModel.getUser().getValue());
        startActivityForResult(intent, REQUEST_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_USER) {
            bd.setUser(viewModel.getUser().getValue());
        }
    }
}
