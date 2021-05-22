package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBbean;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import static utils.DBbean.isIdMark;

public class FaceDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public FaceDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public ArrayList<Face> retrieveFace() {


        ArrayList<Face> faces = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement("Select * from face");
            ResultSet rs = pstmt.executeQuery();

            byte[] toLocalPath ;
            while (rs.next()){

                Student student = new StudentDAO().retrieveStudentByID(rs.getInt(4));
                faces.add(new Face(rs.getBinaryStream(2),  rs.getInt(3), student));

                FileOutputStream fs = new FileOutputStream("src/resources/images/dataset/"
                        +rs.getInt(4)+"-"+student.getStudentName()+"_"+rs.getInt(3)+".jpg");
                toLocalPath = rs.getBlob(2).getBytes(1, (int)rs.getBlob(2).length());
                fs.write(toLocalPath);
            }
            System.out.println("Face retrieved done");
            return faces;

        } catch (SQLException | IOException e) {}

        return null;
    }

    public void insert(Face face) throws SQLException {

        System.out.println("face student id "+face.getStudent().getStudentId());
        System.out.println("face data "+face.getFaceData());
        System.out.println("face set "+face.getFaceSet());

        pstmt = conn.prepareStatement("INSERT INTO face (fData, fSet, f_sId) VALUES(?,?,?)");
        pstmt.setBinaryStream(1, face.getFaceData());
        pstmt.setInt(2, face.getFaceSet());
        pstmt.setInt(3, face.getStudent().getStudentId());

        //Executing the statement
        pstmt.execute();
        System.out.println("face inserted......");

    }



}
