package com.nor.qldiemdanh.model;

import com.nor.qldiemdanh.common.Const;

public class Admin extends User{
    @Override
    public String getRoot() {
        return Const.ROOT_ADMIN;
    }
}
