package com.nor.qldiemdanh.model;

public class ScheduleQR {
    private String id;
    private String idRoom;
    private String idSubject;
    private String idTeacher;

    public ScheduleQR(String id, String idRoom, String idSubject, String idTeacher) {
        this.id = id;
        this.idRoom = idRoom;
        this.idSubject = idSubject;
        this.idTeacher = idTeacher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
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
}
