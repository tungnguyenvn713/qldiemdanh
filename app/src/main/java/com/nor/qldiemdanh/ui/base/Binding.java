package com.nor.qldiemdanh.ui.base;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.nor.qldiemdanh.AppContext;
import com.nor.qldiemdanh.AppViewModel;
import com.nor.qldiemdanh.R;
import com.nor.qldiemdanh.model.Entity;
import com.nor.qldiemdanh.model.Schedule;
import com.nor.qldiemdanh.model.ScheduleQR;
import com.nor.qldiemdanh.model.Student;
import com.nor.qldiemdanh.model.StudentQR;
import com.nor.qldiemdanh.views.MDatePicker;
import com.nor.qldiemdanh.views.QRCodeRender;

public class Binding {
    @BindingAdapter("android:src")
    public static void setImageFromUrl(ImageView imageView, String img) {
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.ic_account)
                .placeholder(R.drawable.ic_account)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(imageView.getContext())
                .load(img).apply(options).into(imageView);
    }

    public static void setImageFromUri(ImageView imageView, Uri img) {
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.ic_account)
                .placeholder(R.drawable.ic_account)
                .circleCrop();
        Glide.with(imageView.getContext())
                .load(img).apply(options).into(imageView);
    }

    @BindingAdapter("android:text")
    public static void setText(TextView tv, @StringRes int v) {
        tv.setText(v);
    }

    @BindingAdapter("android:text")
    public static void setText(TextView tv, float v) {
        tv.setText(String.valueOf(v));
    }

    @BindingAdapter("app:set_date")
    public static void setDate(MDatePicker tv, String date) {
        tv.setDate(date);
    }

    @BindingAdapter("app:set_lesson_from")
    public static void setLessonFrom(TextView tv, int lesson) {
        tv.setText("Tiết bắt đầu: " + lesson);
    }

    @BindingAdapter("app:set_lesson_to")
    public static void setLessonTo(TextView tv, int lesson) {
        tv.setText("Tiết kết thúc: " + lesson);
    }

    @BindingAdapter("app:set_day")
    public static void setDay(TextView tv, int day) {
        tv.setText("Thứ " + (day + 2));
    }

    @BindingAdapter("app:render")
    public static void generate(QRCodeRender render, Entity entity) {
        if (entity == null) return;
        if (entity instanceof Student) {
            render.generate(((Student) entity).toQR());
            return;
        }
        Schedule schedule = (Schedule) entity;
        if (AppContext.getInstance().isStudent) {
            render.setVisibility(View.GONE);
            return;
        }
        ScheduleQR qr = new ScheduleQR(schedule.getId(), schedule.getIdRoom(), schedule.getIdSubject(),
                schedule.getIdTeacher());
        render.generate(qr);
    }
}
