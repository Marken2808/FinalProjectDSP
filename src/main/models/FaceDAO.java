package main.models;

import main.utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class FaceDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public FaceDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public ArrayList<Face> retrieveFace(){
        return null;
    }

    public void insertFace(Face face) throws SQLException {

        pstmt = conn.prepareStatement("INSERT INTO face (fData, fSet, f_sId) VALUES(?,?,?)");
        pstmt.setString(1, face.getFaceData());
        pstmt.setInt(2, face.getFaceSet());
        pstmt.setInt(3, face.getStudent().getStudentId());

        //Executing the statement
        pstmt.execute();
        System.out.println("face inserted......");



    }



}
