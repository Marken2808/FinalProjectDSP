package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.models.Face;
import main.models.Student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

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

//    public static int getLength(){
//        try {
////            pstmt = conn.prepareStatement("Select * from Face");
////            ResultSet rs = pstmt.executeQuery()
//
//            Statement stmt = conn.createStatement();
//            //Retrieving the data
//            ResultSet rs = stmt.executeQuery("select count(*) from face");
//            rs.next();
//            //Moving the cursor to the last row
//            System.out.println("Table contains "+rs.getInt("count(*)")+" rows");
//
//            return rs.getInt("count(*)");
////            if(rs.last()){
////                System.out.println(rs.getRow());
////                return rs.getRow();
////            } else {
////                return 0; //just cus I like to always do some kinda else statement.
////            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }


    public static ObservableList<Student> getStudentData(){
        ObservableList<Student> studentLists = FXCollections.observableArrayList();
        try {
            pstmt = conn.prepareStatement("Select * from Student where sid");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                studentLists.add(new Student(
                        rs.getInt("sId"),
                        rs.getString("sName"),
                        isIdMark(rs.getInt("sId"))
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


}

