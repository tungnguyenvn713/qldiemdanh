package com.nor.qldiemdanh.model;

public class Register extends Entity{
    private String idStudent;

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    @Override
    public String getRoot() {
        return null;
    }
}
