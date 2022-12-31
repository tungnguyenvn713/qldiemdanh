package com.nor.qldiemdanh.model;

import com.nor.qldiemdanh.common.Const;

public class Teacher extends User{
    private String degree;

    @Override
    public String getRoot() {
        return Const.ROOT_TEACHER;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

}
