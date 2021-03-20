package main.models;

public class Teacher extends User{

    private int teacherId;
    private String teacherName;
    private String teacherUsername;
    private String teacherPassword;

    public Teacher(int teacherId, String teacherName){
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public Teacher(String teacherName, String teacherUsername, String teacherPassword) {
        super(teacherUsername, teacherPassword);
        this.teacherName = teacherName;
        this.teacherUsername = teacherUsername;
        this.teacherPassword = teacherPassword;
    }

    public Teacher(String teacherUsername, String teacherPassword) {
        super(teacherUsername, teacherPassword);
        this.teacherUsername = teacherUsername;
        this.teacherPassword = teacherPassword;
    }

    public int getTeacherID() {
        return teacherId;
    }

    public void setTeacherID(int teacherID) {
        this.teacherId = teacherID;
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
