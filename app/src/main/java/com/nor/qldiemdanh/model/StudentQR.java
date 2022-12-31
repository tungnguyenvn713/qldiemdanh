package com.nor.qldiemdanh.model;

public class StudentQR extends Entity{
    private String name;
    private String email;

    public StudentQR(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String getRoot() {
        return null;
    }
}
