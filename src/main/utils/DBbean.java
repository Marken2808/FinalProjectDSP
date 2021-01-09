package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import main.models.Student;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.Random;

public class DBbean {

    public static Connection conn;
    public static PreparedStatement pstmt;

    public static void getConnection() {

        String mysqlUrl = "jdbc:mysql://localhost:3306/schoolmana";
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(mysqlUrl, "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established......");

    }

    public static ObservableList<Student> getStudentData(){
        ObservableList<Student> studentLists = FXCollections.observableArrayList();
        try {
            pstmt = conn.prepareStatement("Select * from Student");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                studentLists.add(new Student(
                        rs.getInt("sId"),
                        rs.getString("sName")
                ));
            }
            System.out.println("accessed successfully");
        } catch (SQLException throwables) {
            System.out.println("cannot access student table");
        }
        return studentLists;
    }

    public static void insertStudent(int sid, String sname) {

        try {
            pstmt = conn.prepareStatement("INSERT INTO student (sId,sName) VALUES(?,?)");
            pstmt.setInt(1, sid);
            pstmt.setString(2, sname);
            //Executing the statement
            pstmt.execute();
            System.out.println("student inserted......");
        } catch (SQLException e) {
            System.out.println("student already exist......");
        }

    }

    public static void insertFace(String data, int set, int sid) throws SQLException {

            pstmt = conn.prepareStatement("INSERT INTO face (fData, fSet, f_sId) VALUES(?,?,?)");

            pstmt.setString(1, data);
            pstmt.setInt(2, set);
            pstmt.setInt(3, sid);

            //Executing the statement
            pstmt.execute();
            System.out.println("face inserted......");

    }
}

