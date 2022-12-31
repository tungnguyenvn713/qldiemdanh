package com.nor.qldiemdanh.model;

import com.nor.qldiemdanh.common.Const;

public class CheckIn extends Entity{
    private long date;
    private String idStudent;
    private String idSchedule;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    @Override
    public String getRoot() {
        return Const.ROOT_CHECK_IN;
    }
}
