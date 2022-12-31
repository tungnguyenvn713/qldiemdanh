package com.nor.qldiemdanh.model;

import com.nor.qldiemdanh.common.Const;

public class ScheduleDetail extends Entity{
    private int day;
    private int lessonFrom;
    private int lessonTo;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getLessonFrom() {
        return lessonFrom;
    }

    public void setLessonFrom(int lessonFrom) {
        this.lessonFrom = lessonFrom;
    }

    public int getLessonTo() {
        return lessonTo;
    }

    public void setLessonTo(int lessonTo) {
        this.lessonTo = lessonTo;
    }

    @Override
    public String getRoot() {
        return Const.ROOT_SCHEDULE_DETAIL;
    }
}
