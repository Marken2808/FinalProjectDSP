package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import main.models.Attendance;
import main.models.Face;
import main.models.Module;
import main.models.Student;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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

//    select ----------------------------------------------------

    public static ObservableList<Student> showStudentTable(){
        ObservableList<Student> studentLists = FXCollections.observableArrayList();
        try {
            pstmt = conn.prepareStatement("Select * from Student");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                studentLists.add(new Student(
                        rs.getInt("sId"),
                        rs.getString("sName"),
                        isIdMark(rs.getInt("sId")),
                        getLast5Days(rs.getInt("sId"))
                ));
            }
//            System.out.println("accessed successfully");
        } catch (SQLException throwables) {
            System.out.println("cannot access student table");
        }
        return studentLists;
    }

    public static double[] getModuleData(int sid){
        try {
            pstmt = conn.prepareStatement("Select * from modules where m_sId="+sid);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numOfCols = rsmd.getColumnCount();
            double[] moduleData = new double[numOfCols];
            while (rs.next()){
                for(int i = 0; i<numOfCols-1 ; i++){
                    moduleData[i] = rs.getDouble(i+1);
                }
            }
            return moduleData;
//            System.out.println("accessed successfully");
        } catch (SQLException e) {
            System.out.println("cannot access module table");
        }
        return null;
    }

    public static boolean isIdMark(int sid){
        try {
            pstmt = conn.prepareStatement(
                    "select  m_sId from modules where \n" +
                            "mMath is null or \n" +
                            "mPhysics is null or \n" +
                            "mChemistry is null or\n" +
                            "mEnglish is null or\n" +
                            "mHistory is null or\n" +
                            "mBiology is null or\n" +
                            "mGeography is null"
            );
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                if(sid == rs.getInt(1)){
                    return false;
                }
            }
//            System.out.println("checked id null mark......");
        } catch (SQLException e) {
            System.out.println("cannot access null mark table......");
        }
        return true;
    }

    public static ArrayList<Label> getLast5Days(int sid){

//        ArrayList<Timestamp> test = new ArrayList<>();
        ArrayList<Label> test = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement("Select aDate, aStatus from Attendance where a_sId="+sid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
//                test.add(rs.getTimestamp(1));
                String date = new SimpleDateFormat("yyyy-MM-dd").format(
                        new Date(rs.getTimestamp(1).getTime())
                );
                int status = rs.getInt(2);

                if(status==1){
                    Label label = new Label("P");
                    label.setStyle("-fx-background-color: limegreen");
                    test.add(label);
                } else {
                    Label label = new Label("A");
                    label.setStyle("-fx-background-color: palevioletred");
                    test.add(label);
                }
                
//                test.add(date);
            }
            System.out.println("test: "+test);
            return test;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

//    retrieve -----------------------------------------------------------

    public static ArrayList<Student> retrieveStudent(){
        return null;
    }

    public static ArrayList<Face> retrieveFace(){
        return null;
    }

    public static ArrayList<Module> retrieveModule(){
        return null;
    }

    public static ArrayList<Attendance> retrieveAttendance(){
        try {
            pstmt = conn.prepareStatement("Select * from Attendance");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Attendance> test = new ArrayList<>();
            while (rs.next()){
                test.add(new Attendance(
                        rs.getTimestamp(1),
                        rs.getInt(2),
                        rs.getInt(3)
                ));
//                System.out.println(rs.getTimestamp(1) + " - " + rs.getInt(2) + " - "+ rs.getInt(3));
            }
//            System.out.println("accessed successfully");
            return test;
        } catch (SQLException throwables) {
//            System.out.println("cannot access student table");
        }
        return null;
    }


//    insert -----------------------------------------------------

    public static void insertStudent(Student student) {

        try {
            pstmt = conn.prepareStatement("INSERT INTO student (sId,sName) VALUES(?,?)");
            pstmt.setInt(1, student.getStudentId());
            pstmt.setString(2, student.getStudentName());
            //Executing the statement
            pstmt.execute();
            System.out.println("student inserted......");
            insertModule(student.getStudentId());
        } catch (SQLException e) {
            System.out.println("student already exist......");
        }

    }

    public static void insertModule(int sid){
        try {
            pstmt = conn.prepareStatement("INSERT INTO modules (m_sId) VALUES(?)");
            pstmt.setInt(1, sid);
            //Executing the statement
            pstmt.execute();
            System.out.println("module inserted......");
        } catch (SQLException e) {
            System.out.println("module already exist......");
        }
    }

    public static void insertFace(Face face) throws SQLException {

        pstmt = conn.prepareStatement("INSERT INTO face (fData, fSet, f_sId) VALUES(?,?,?)");
        pstmt.setString(1, face.getFaceData());
        pstmt.setInt(2, face.getFaceSet());
        pstmt.setInt(3, face.getStudent().getStudentId());

        //Executing the statement
        pstmt.execute();
        System.out.println("face inserted......");



    }

    public static void insertAttendance(Attendance attendance){
        try {
            pstmt = conn.prepareStatement("INSERT INTO attendance (aStatus, a_sId) VALUES(?,?)");
            pstmt.setInt(1,attendance.getAttStatus());
            pstmt.setInt(2,attendance.getStudentID());

            //Executing the statement
            pstmt.execute();
            System.out.println("attendance inserted......");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

