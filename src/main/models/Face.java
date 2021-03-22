package models;

public class Face{

    private int faceID;
    private String faceData;
    private int faceSet;
    private Student student;


    public Face(String faceData, int faceSet, Student student) {
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

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
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
