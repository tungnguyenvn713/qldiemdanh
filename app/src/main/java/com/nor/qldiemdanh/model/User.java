package com.nor.qldiemdanh.model;

import android.text.TextUtils;

public abstract class User extends Entity {
    private String name;
    private String dob;
    private String phone;
    private String email;
    private String address;
    private String password;

    public String getImg() {
        return "https://firebasestorage.googleapis.com/v0/b/qldiemdanh.appspot.com/o/image%2F" + getId() + "?alt=media";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        if (TextUtils.isEmpty(password)) {
            password = email;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin(){
        return this instanceof Admin;
    }

    public boolean isTeacher(){
        return this instanceof Teacher;
    }

    public boolean isStudent(){
        return this instanceof Student;
    }
}
