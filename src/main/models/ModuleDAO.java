package main.models;

import main.utils.DBbean;

import java.sql.*;
import java.util.ArrayList;

public class ModuleDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public ModuleDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public static ArrayList<Module> retrieveModule(){
        return null;
    }


    public double[] getModuleData(int sid){
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
//            System.out.println("accessed module successfully");
            return moduleData;
        } catch (SQLException e) {
//            System.out.println("cannot access module table");
        }
        return null;
    }

    public void insertModule(int sid){
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


}
