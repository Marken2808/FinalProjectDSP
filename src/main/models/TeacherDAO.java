package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static utils.DBbean.isIdMark;

public class TeacherDAO {
    private Connection conn;
    private PreparedStatement pstmt;

    public TeacherDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public ObservableList<Teacher> showTeacherTable(){
        ObservableList<Teacher> teacherLists = FXCollections.observableArrayList();
        try {
            pstmt = conn.prepareStatement("Select * from Teacher");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                teacherLists.add(new Teacher(
                        rs.getInt("tId"),
                        rs.getString("tName"),
                        rs.getInt("t_uId")
                ));
            }
//            System.out.println("accessed successfully");
        } catch (SQLException throwables) {
            System.out.println("cannot access teacher table");
        }
        return teacherLists;
    }

    public Teacher retrieve(int userID) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM teacher WHERE t_uId = " + userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return  new Teacher(
                    rs.getInt("tId"),
                    rs.getString("tName"),
                    rs.getInt("t_uId")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Teacher teacher){
        try {
            pstmt = conn.prepareStatement("INSERT INTO teacher (tName, t_uId) VALUES(?,?)");
            pstmt.setString(1,"Default");
            pstmt.setInt(2, teacher.getUserID());

            System.out.println("-> "+teacher.getUserID());
            //Executing the statement
            pstmt.execute();
            System.out.println("teacher inserted......");
        } catch (SQLException e) {
            System.out.println("teacher already exist......");

        }
    }


}
