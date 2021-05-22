package models;

public class Teacher extends User{

    private int teacherId;
    private String teacherName;
    private int teacherUserId;

    public Teacher(){

    }

    public Teacher(int teacherId, String teacherName, int teacherUserId){
        super(teacherUserId);
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherUserId = teacherUserId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeacherUserId() {
        return teacherUserId;
    }

    public void setTeacherUserId(int teacherUserId) {
        this.teacherUserId = teacherUserId;
    }





}
