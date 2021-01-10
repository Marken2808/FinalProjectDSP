package main.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.models.Student;

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

    public static void insertStudent(int sid, String sname) {

        try {
            pstmt = conn.prepareStatement("INSERT INTO student (sId,sName) VALUES(?,?)");
            pstmt.setInt(1, sid);
            pstmt.setString(2, sname);
            //Executing the statement
            pstmt.execute();
            System.out.println("student inserted......");
            insertModule(sid);
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

