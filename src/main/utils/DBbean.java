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
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DBbean {

    public static Connection conn;
    public static PreparedStatement pstmt;

    public static Connection getConnection() {

        String mysqlUrl = "jdbc:mysql://localhost:3306/schoolmana";
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(mysqlUrl, "root", "123456");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established......");
        return null;
    }

    public static PreparedStatement getPreparedStatement() {
        return pstmt;
    }


    public static boolean isIdMark(int sid){
        try {
            pstmt = conn.prepareStatement(
                    "select  m_sId from `module` where \n" +
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

}

