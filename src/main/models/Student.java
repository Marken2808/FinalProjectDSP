package models;


import java.util.ArrayList;

public class Student extends User{

    private int studentId;
    private String studentName;
    private boolean studentMarked;
    private ArrayList<Attendance> studentLast5Days;
    private int studentUserId;

    public Student() {
    }

    public Student(int sid) {
        this.studentId = sid;
    }

    public Student(int sid, String sname){      //for add face student
        this.studentId = sid;
        this.studentName = sname;
    }

    public Student(int sid, String sname, int studentUserId){   //test with user table
        super(studentUserId);
        this.studentId = sid;
        this.studentName = sname;
        this.studentUserId = studentUserId;
    }

    public Student(int sid, String sname, boolean marked, ArrayList<Attendance> studentLast5Days) {
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

//    public void setStudentMarked(boolean studentMarked) {
//        this.studentMarked = studentMarked;
//    }

    public ArrayList<Attendance> getStudentLast5Days() {
        return studentLast5Days;
    }

//    public void setStudentLast5Days(ArrayList<Attendance> studentLast5Days) {
//        this.studentLast5Days = studentLast5Days;
//    }



    @Override
    public String toString() {
        return "Student: " +studentId+ " : " + studentName ;
    }


}
