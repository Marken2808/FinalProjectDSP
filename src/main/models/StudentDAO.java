package main.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static main.utils.DBbean.isIdMark;

public class StudentDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public StudentDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public ArrayList<Student> retrieveStudent(){
        return null;
    }

    public void insert(Student student) {

        try {
            pstmt = conn.prepareStatement("INSERT INTO student (sId,sName) VALUES(?,?)");
            pstmt.setInt(1, student.getStudentId());
            pstmt.setString(2, student.getStudentName());
            //Executing the statement
            pstmt.execute();
            System.out.println("student inserted......");
//            insertModule(student.getStudentId());
        } catch (SQLException e) {
            System.out.println("student already exist......");
        }

    }

    public ObservableList<Student> showStudentTable(){
        ObservableList<Student> studentLists = FXCollections.observableArrayList();
        try {
            pstmt = conn.prepareStatement("Select * from Student");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                studentLists.add(new Student(
                        rs.getInt("sId"),
                        rs.getString("sName"),
                        isIdMark(rs.getInt("sId")),
                        new Attendance().getLast5days(rs.getInt("sId"))
                ));
            }
//            System.out.println("accessed successfully");
        } catch (SQLException throwables) {
            System.out.println("cannot access student table");
        }
        return studentLists;
    }


}
