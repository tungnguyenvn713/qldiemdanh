package com.nor.qldiemdanh.model;

import com.nor.qldiemdanh.common.Const;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends Entity {
    private String idRoom;
    private String idSubject;
    private String idTeacher;
    private Subject subject;
    private Teacher teacher;
    private String roomName;
    private boolean isRegistered;
    private List<ScheduleDetail> details = new ArrayList<>();

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<ScheduleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ScheduleDetail> details) {
        this.details = details;
    }

    @Override
    public String getRoot() {
        return Const.ROOT_SCHEDULE;
    }
}
