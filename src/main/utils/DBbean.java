package main.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBbean {

    static Connection conn;

    public static void getConnection(){

        String mysqlUrl = "jdbc:mysql://localhost:3306/schoolmana";
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(mysqlUrl, "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Connection established......");


        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO student (sid,sname,sface) VALUES(?,?,?)");
            pstmt.setInt(1, 111);
            pstmt.setString(2, "aaa");
            //Inserting Blob type
            InputStream in = new FileInputStream("src/resources/images/input/1a.jpg");
            pstmt.setBlob(3, in);
            //Executing the statement
            pstmt.execute();
            System.out.println("Record inserted......");
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
