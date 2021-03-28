package models;

import utils.DBbean;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


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
                        )
                );
//                System.out.println(rs.getDate(1) + " - " + rs.getString(2) + " - "+ rs.getInt(3));
            }
            System.out.println("accessed successfully");
            return test;
        } catch (SQLException throwables) {
            System.out.println("cannot access student table");
        }
        return null;
    }

//    public HashMap<LocalDate, String> countInDay(ArrayList<LocalDate> last5Days){
//        try {
//            pstmt = conn.prepareStatement("Select * from Attendance");
//            ResultSet rs = pstmt.executeQuery();
//            ArrayList<HashMap<LocalDate, String>> test = new ArrayList<>();
//            HashMap<LocalDate, String> map = new HashMap<>();
//
//            while (rs.next()){
//                    map.put(rs.getDate(1).toLocalDate(), rs.getString(2));
//                    test.add(map);
//            }
//
//            for (int i = 0; i < last5Days.size(); i++) {
//                System.out.println(last5Days.get(i) + " : " + map.get(last5Days.get(i)));
//
//            }
//
//            System.out.println("Test: "+test);
//
//            System.out.println("accessed successfully");
//            return map;
//        } catch (SQLException throwables) {
//            System.out.println("cannot access student table");
//        }
//        return null;
//    }

    public void insert(Attendance attendance){

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
            System.out.println("attendance today already exist......");

        }
    }

    public void update(Attendance attendance){
        try {
            String sql= "UPDATE `attendance` " +
                    "SET aStatus  = '" + attendance.getAttStatus() + "' "+
                    "WHERE a_sId = " + attendance.getStudentID();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("attendance updated......");
        } catch (SQLException e) {
            System.out.println("attendance today already exist......");
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
