package com.nor.qldiemdanh;

import android.app.Application;

public class AppContext extends Application {
    private static AppContext instance;
    public boolean isStudent;
    public boolean isTeacher;
    public boolean isAdmin;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public void reset() {
        isStudent = isTeacher = isAdmin = false;
    }
}
