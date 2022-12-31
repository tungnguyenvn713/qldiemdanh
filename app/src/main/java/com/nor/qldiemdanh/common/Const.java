package com.nor.qldiemdanh.common;

public interface Const {
    String DB_ROOT = "QLSV";
    String ROOT_SCIENCE = "Science";
    String ROOT_SEMESTER = "Semester";
    String ROOT_TEACHER = "Teacher";
    String ROOT_SUBJECT = "Subject";
    String ROOT_CLASS = "ClassRoom";
    String ROOT_STUDENT = "Student";
    String ROOT_SCHEDULE = "Schedule";
    String ROOT_SCORE = "Score";
    String ROOT_ADMIN = "Admin";
    String ROOT_ROOM = "Room";
    String ROOT_SCHEDULE_DETAIL = "details";
    String ROOT_REGISTER = "Register";
    String ROOT_CHECK_IN = "CheckIn";

    enum CHECK_IN_STATUS{
        NOT_IN,
        NOT_TODAY,
        SUCCESS
    }
}
