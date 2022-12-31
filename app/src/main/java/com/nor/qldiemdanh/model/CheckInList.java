package com.nor.qldiemdanh.model;

import java.util.ArrayList;
import java.util.Collection;

public class CheckInList {
    private String date;
    private ArrayList<CheckIn> data = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<CheckIn> getData() {
        return data;
    }

    public void addValue(CheckIn checkIn){
        data.add(checkIn);
    }
}
