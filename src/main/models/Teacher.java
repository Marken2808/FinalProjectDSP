package main.models;

public class Teacher extends User{

    private int teacherID;
    private String teacherName;
    private String teacherUsername;
    private String teacherPassword;

    public Teacher(int teacherID, String teacherName, String teacherUsername, String teacherPassword) {
        super(teacherUsername, teacherPassword);
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.teacherUsername = teacherUsername;
        this.teacherPassword = teacherPassword;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }
}
