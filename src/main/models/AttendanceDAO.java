package main.models;

import main.utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceDAO {

    public Connection conn;
    public PreparedStatement pstmt;

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

}
