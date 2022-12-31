package com.nor.qldiemdanh.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected String id = System.currentTimeMillis() + "";

    public String getId() {
        return id;
    }

    public abstract String getRoot();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity == false) return false;
        Entity entity = (Entity) obj;
        return entity.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return 7;
    }
}
