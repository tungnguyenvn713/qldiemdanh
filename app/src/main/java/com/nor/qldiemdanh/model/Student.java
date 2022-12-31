package com.nor.qldiemdanh.model;

import com.nor.qldiemdanh.common.Const;

public class Student extends User {
    private String sex;

    @Override
    public String getRoot() {
        return Const.ROOT_STUDENT;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public StudentQR toQR(){
        return new StudentQR(getId(), getName(), getEmail());
    }

}
