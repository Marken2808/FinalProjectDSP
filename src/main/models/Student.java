package main.models;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Student {

    private int studentId;
    private String studentName;
    private boolean studentMarked;
    private ObservableList<String> studentLast5Days;

    public Student(int sid, String sname){
        this.studentId = sid;
        this.studentName = sname;
    }

    public Student(int sid, String sname, boolean marked, ObservableList<String> studentLast5Days) {
        this.studentId = sid;
        this.studentName = sname;
        this.studentMarked = marked;
        this.studentLast5Days = studentLast5Days;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isStudentMarked() {
        return studentMarked;
    }

    public void setStudentMarked(boolean studentMarked) {
        this.studentMarked = studentMarked;
    }

    public ObservableList<String> getStudentLast5Days() {
        return studentLast5Days;
    }

    public void setStudentLast5Days(ObservableList<String> studentLast5Days) {
        this.studentLast5Days = studentLast5Days;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentMarked=" + studentMarked +
                '}';
    }
}
