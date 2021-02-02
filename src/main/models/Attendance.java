package main.models;



import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static main.utils.DBbean.*;

public class Attendance {

    private Date attDate;
    private String attStatus;
    private int studentID;
    private ArrayList<Attendance> test5Days;

    public Attendance() {
    }

    public Attendance(Date attDate, String attStatus) {
        this.attDate = attDate;
        this.attStatus = attStatus;
    }

    public Attendance(String attStatus, int studentID) {
        this.attStatus = attStatus;
        this.studentID = studentID;
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

    public ArrayList<Attendance> getTest5Days(int sid) {

        this.studentID = sid;

        ArrayList<Attendance> test = new ArrayList<>();
        HashMap<Integer, Attendance> map = new HashMap<>();

        ArrayList<LocalDate> days = new ArrayList<>();
        days.add(LocalDate.now());
        for (int i = 1; i < 5; i++) {
            days.add(days.get(i - 1).minusDays(1));
        }
//        System.out.println(days);

//        System.out.println("id= "+ getStudentID());
        try {
            pstmt = conn.prepareStatement("Select aDate, aStatus from Attendance where a_sId=" + studentID + " ORDER BY aDate ASC");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Date date = rs.getDate(1);
                String status = rs.getString(2);

                if (days.contains(date.toLocalDate())) {
//                    System.out.println("S: "+date+"--"+status);
                    map.put(studentID, new Attendance(date, status));
                    test.add(map.get(studentID));

                }
            }
//            System.out.println(test);
            return test;
//            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
