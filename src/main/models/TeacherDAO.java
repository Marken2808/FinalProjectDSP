package main.models;

import main.utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDAO {
    private Connection conn;
    private PreparedStatement pstmt;

    public TeacherDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public void insert(Teacher teacher){
        try {
            pstmt = conn.prepareStatement("INSERT INTO teacher (tUsername, tPassword, tName) VALUES(?,?,?)");
            pstmt.setString(1,teacher.getTeacherUsername());
            pstmt.setString(2,teacher.getTeacherPassword());
            pstmt.setString(3,teacher.getTeacherName());

            //Executing the statement
            pstmt.execute();
            System.out.println("teacher inserted......");
        } catch (SQLException e) {
            System.out.println("teacher already exist......");

        }
    }

    public boolean authenticate (Teacher teacher) {

        try {
            pstmt = conn.prepareStatement("SELECT * FROM teacher " +
                    "WHERE tUsername= '" + teacher.getTeacherUsername() + "' " +
                    "AND tPassword= '" + teacher.getTeacherPassword() +"'");
            return pstmt.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("No teacher found");
        }
        return false;
    }
}
