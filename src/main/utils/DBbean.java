package main.utils;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.Random;

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



    }

    public static void uploadImageDB(File file, Image img){
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO student (sid,sname,sface) VALUES(?,?,?)");
            pstmt.setInt(1, new Random().nextInt());
            pstmt.setString(2, file.getName());
            //Inserting Blob type
            InputStream in = new FileInputStream(file.getPath());
            pstmt.setBlob(3, in);
            //Executing the statement
            pstmt.execute();
            System.out.println("Record inserted......");
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public static Image retrieveImageDB(){
//        try {
//            PreparedStatement pstmt = conn.prepareStatement("SELECT sFace FROM student where sID = 1682480429");
//            ResultSet rs = pstmt.executeQuery();
//            Blob blob = rs.getBlob(column);
//            InputStream in = blob.getBinaryStream();
//            BufferedImage image = ImageIO.read(in);
//            return image;
//        } catch (IOException | SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
