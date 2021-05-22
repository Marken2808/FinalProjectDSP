package models;

import java.io.InputStream;

public class Face{

    private int faceID;
    private InputStream faceData;
    private int faceSet;
    private int studentID;
    private Student student;

    public Face(InputStream faceData, int faceSet, int studentID) {
        this.faceData = faceData;
        this.faceSet = faceSet;
        this.studentID = studentID;
    }

    public Face(InputStream faceData, int faceSet, Student student) {
        this.faceData = faceData;
        this.faceSet = faceSet;
        this.student = student;
    }

    public int getFaceID() {
        return faceID;
    }

    public void setFaceID(int faceID) {
        this.faceID = faceID;
    }

    public InputStream  getFaceData() {
        return faceData;
    }

    public void setFaceData(InputStream  faceData) {
        this.faceData = faceData;
    }

    public int getFaceSet() {
        return faceSet;
    }

    public void setFaceSet(int faceSet) {
        this.faceSet = faceSet;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


}
