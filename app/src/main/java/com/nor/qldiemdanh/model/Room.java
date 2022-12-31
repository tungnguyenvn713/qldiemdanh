package com.nor.qldiemdanh.model;

import com.nor.qldiemdanh.common.Const;

public class Room extends Entity {
    private String name;

    @Override
    public String getRoot() {
        return Const.ROOT_ROOM;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
