package main.models;


import java.sql.Timestamp;

public class Attendance {

    private Timestamp attDate;
    private int attStatus;
    private int studentID;

    public Attendance(int attStatus, int studentID) {
        this.attStatus = attStatus;
        this.studentID = studentID;
    }

    public Attendance(Timestamp attDate, int attStatus, int studentID) {
        this.attDate = attDate;
        this.attStatus = attStatus;
        this.studentID = studentID;
    }


    public Timestamp getAttDate() {
        return attDate;
    }

    public void setAttDate(Timestamp attDate) {
        this.attDate = attDate;
    }

    public int getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(int attStatus) {
        this.attStatus = attStatus;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
}
