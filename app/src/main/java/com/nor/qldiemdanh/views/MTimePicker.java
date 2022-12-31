package com.nor.qldiemdanh.views;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MTimePicker extends android.support.v7.widget.AppCompatEditText implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private static final String FOMATER = "HH:mm";
    private TimePickerDialog dialog;
    private Calendar calendar;
    public MTimePicker(Context context) {
        super(context);
        init();
    }

    public MTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(false);
        setClickable(true);
        setOnClickListener(this);
        this.calendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        dialog = new TimePickerDialog(getContext(), this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
    }
    
    public void setTime(String time){
        try {
            SimpleDateFormat format = new SimpleDateFormat(FOMATER);
            Date d = format.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            dialog = new TimePickerDialog(getContext(), this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
            setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        dialog.show();
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat(FOMATER);
        return format.format(calendar.getTime());
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String time = String.format("%02d:%02d", i, i1);
        setText(time);
    }
}
