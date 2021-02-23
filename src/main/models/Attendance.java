package main.models;



import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class Attendance {

    private Date attDate;
    private String attStatus;
    private int studentID;


    public Attendance() {
    }

    public Attendance(String attStatus, int studentID) {
        this.attStatus = attStatus;
        this.studentID = studentID;
    }

    public Attendance(Date attDate, String attStatus) {
        this.attDate = attDate;
        this.attStatus = attStatus;
    }

    public Attendance(Date attDate, String attStatus, int studentID) {
        this.attDate = attDate;
        this.attStatus = attStatus;
        this.studentID = studentID;
    }


    public Date getAttDate() {
        return attDate;
    }

//    public void setAttDate(Date attDate) {
//        this.attDate = attDate;
//    }

    public String getAttStatus() {
        return attStatus;
    }

//    public void setAttStatus(String attStatus) {
//        this.attStatus = attStatus;
//    }

    public int getStudentID() {
        return studentID;
    }

//    public void setStudentID(int studentID) {
//        this.studentID = studentID;
//    }

    public ArrayList<Attendance> getLast5days(int sid) {

        this.studentID = sid;

        ArrayList<LocalDate> last5days = new ArrayList<>();
        last5days.add(LocalDate.now());
//        System.out.println(last5days);
        for (int i = 1; i < 5; i++) {
            last5days.add(last5days.get(i - 1).minusDays(1));
        }

        return new AttendanceDAO().retrieveDate(studentID, last5days);

    }

    public ArrayList<Attendance> getAllDate(int sid, ArrayList<LocalDate> allDate){
        this.studentID = sid;
        return new AttendanceDAO().retrieveDate(studentID, allDate);
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
