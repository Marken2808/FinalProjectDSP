package main.models;


import java.sql.Date;
import java.sql.Timestamp;

public class Attendance {

    private Date attDate;
    private String attStatus;
    private int studentID;

    public Attendance(Date attDate, String attStatus) {
        this.attDate = attDate;
        this.attStatus = attStatus;
    }

    public Attendance(String attStatus, int studentID) {
        this.attStatus = attStatus;
        this.studentID = studentID;
    }

    public Attendance(Date attDate, String attStatus, int studentID) {
        this.attDate = attDate;
        this.attStatus = attStatus;
        this.studentID = studentID;
    }


    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public String getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(String attStatus) {
        this.attStatus = attStatus;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attDate=" + attDate +
                ", attStatus=" + attStatus +
                ", studentID=" + studentID +
                '}';
    }
}
