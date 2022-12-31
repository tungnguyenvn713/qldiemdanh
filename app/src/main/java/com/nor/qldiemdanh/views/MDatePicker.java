package com.nor.qldiemdanh.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MDatePicker extends android.support.v7.widget.AppCompatEditText implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private static final String FOMATER = "yyyy/MM/dd";
    private DatePickerDialog dialog;
    private Calendar calendar;
    public MDatePicker(Context context) {
        super(context);
        init();
    }

    public MDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(false);
        setClickable(true);
        setOnClickListener(this);
        this.calendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(getContext(), this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
    
    public void setDate(String date){
        try {
            SimpleDateFormat format = new SimpleDateFormat(FOMATER);
            Date d = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            dialog = new DatePickerDialog(getContext(), this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        setText(toString());
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat(FOMATER);
        return format.format(calendar.getTime());
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
