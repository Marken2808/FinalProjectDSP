package main.models;


public class Student {

    private int studentId;
    private String studentName;
    private boolean studentMarked;

    public Student(int sid, String sname){
        this.studentId = sid;
        this.studentName = sname;
    }

    public Student(int sid, String sname, boolean marked) {
        this.studentId = sid;
        this.studentName = sname;
        this.studentMarked = marked;
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
}
