package main.models;

import main.utils.DBbean;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ModuleDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public ModuleDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public ArrayList<Module> select(String query){
        ArrayList<Module> modules = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                modules.add(new Module(
                    rs.getInt(1),
                    new Subject[]{
                        new Subject("Math", rs.getDouble(2)),
                        new Subject("Physics", rs.getDouble(3)),
                        new Subject("Chemistry", rs.getDouble(4)),
                        new Subject("English", rs.getDouble(5)),
                        new Subject("History", rs.getDouble(6)),
                        new Subject("Biology", rs.getDouble(7)),
                        new Subject("Geography", rs.getDouble(8))
                    },
                    rs.getInt(9)
                ));
            }
            System.out.println("module retrieved......");
            return modules;
        } catch (SQLException e) {
            System.out.println("error......");
        }
        return null;
    }

    public Module retrieveModuleByID(int sid){
        ArrayList<Module> test = select("Select * from Modules where m_sid = "+sid);
        return test.get(0);
    }

    public Subject[] retrieveSubjectData(int sid){
        return retrieveModuleByID(sid).getSubjects();
    }

    public void insert(Student student){
        try {
            pstmt = conn.prepareStatement("INSERT INTO modules (m_sId) VALUES(?)");
            pstmt.setInt(1, student.getStudentId());
            //Executing the statement
            pstmt.execute();
            System.out.println("module inserted......");
        } catch (SQLException e) {
            System.out.println("module already exist......");
        }
    }

    public void update(int sid, HashMap<String, Object> map){

        System.out.println("test: "+map.get("Math"));

        try {

            String sql= "UPDATE modules " +
                        "SET mMath      = '" + map.get("Math"       ) + "' ," +
                        "    mPhysics   = '" + map.get("Physics"    ) + "' ," +
                        "    mChemistry = '" + map.get("Chemistry"  ) + "' ," +
                        "    mEnglish   = '" + map.get("English"    ) + "' ," +
                        "    mHistory   = '" + map.get("History"    ) + "' ," +
                        "    mBiology   = '" + map.get("Biology"    ) + "' ," +
                        "    mGeography = '" + map.get("Geography"  ) + "'  " +

                        "WHERE m_sid = " + sid;
            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setString(1, "John");

            pst.executeUpdate();
            System.out.println("Updated Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
