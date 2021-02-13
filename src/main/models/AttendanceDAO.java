package main.models;

import main.utils.DBbean;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static main.utils.DBbean.conn;
import static main.utils.DBbean.pstmt;

public class AttendanceDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public AttendanceDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public ArrayList<Attendance> retrieveAttendance(){
        try {
            pstmt = conn.prepareStatement("Select * from Attendance");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Attendance> test = new ArrayList<>();

            while (rs.next()){
                test.add(new Attendance(
                        rs.getDate(1),
                        rs.getString(2),
                        rs.getInt(3)
                ));
//                System.out.println(rs.getDate(1) + " - " + rs.getString(2) + " - "+ rs.getInt(3));
            }
//            System.out.println("accessed successfully");
            return test;
        } catch (SQLException throwables) {
//            System.out.println("cannot access student table");
        }
        return null;
    }

    public void insertAttendance(Attendance attendance){

        Date date = Date.valueOf(LocalDate.now());

        try {
            pstmt = conn.prepareStatement("INSERT INTO attendance (aDate, aStatus, a_sId) VALUES(?,?,?)");
            pstmt.setDate(1, date);
            pstmt.setString(2,attendance.getAttStatus());
            pstmt.setInt(3,attendance.getStudentID());

            //Executing the statement
            pstmt.execute();
            System.out.println("attendance inserted......");
        } catch (SQLException e) {
            System.out.println("attendance failed......");
            e.printStackTrace();
        }
    }

    public ArrayList<Attendance> retrieveDate(int sid, ArrayList<LocalDate> days) {

        ArrayList<Attendance> test = new ArrayList<>();
        HashMap<Integer, Attendance> map = new HashMap<>();

        try {
            pstmt = conn.prepareStatement("Select aDate, aStatus from Attendance where a_sId=" + sid + " ORDER BY aDate ASC");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Date date = rs.getDate(1);
                String status = rs.getString(2);

                if (days.contains(date.toLocalDate())) {    // make sure in range days
//                    System.out.println("S: "+date+"--"+status);
                    map.put(sid, new Attendance(date, status));
                    test.add(map.get(sid));
                }
            }
//            System.out.println(test);
            return test;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
