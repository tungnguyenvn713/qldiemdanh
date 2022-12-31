package com.nor.qldiemdanh.model;


import com.nor.qldiemdanh.common.Const;

public class Subject extends Entity {
    private String name;
    private int lesson;
    private float level;

    @Override
    public String getRoot() {
        return Const.ROOT_SUBJECT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLesson() {
        return lesson;
    }

    public String getLessonString() {
        return lesson +" tiết";
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public float getLevel() {
        return level;
    }

    public String getLevelString() {
        return level + " tín";
    }

    public void setLevel(float level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return name;
    }
}
