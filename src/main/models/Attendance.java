package main.models;

import java.sql.Date;

public class Attendance {
    private Date attDate;
    private boolean attStatus;
    private Student student;

    public Attendance(Date attDate, boolean attStatus, Student student) {
        this.attDate = attDate;
        this.attStatus = attStatus;
        this.student = student;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public boolean isAttStatus() {
        return attStatus;
    }

    public void setAttStatus(boolean attStatus) {
        this.attStatus = attStatus;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
