package models;

import utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        ArrayList<Module> test = select("Select * from Module where m_sid = "+sid);
        return test.get(0);
    }

    public Subject[] retrieveSubjectData(int sid){
        return retrieveModuleByID(sid).getSubjects();
    }

    public void insert(Student student){

        try {
            pstmt = conn.prepareStatement("INSERT INTO `module` " +
                    "(mMath, mPhysics, mChemistry, mEnglish, mHistory, mBiology, mGeography, m_sId)" +
                    "VALUES (?,?,?,?,?,?,?,?)");
            pstmt.setDouble(1,0);
            pstmt.setDouble(2,0);
            pstmt.setDouble(3,0);
            pstmt.setDouble(4,0);
            pstmt.setDouble(5,0);
            pstmt.setDouble(6,0);
            pstmt.setDouble(7,0);
            pstmt.setInt(8, student.getStudentId());
            //Executing the statement
            pstmt.execute();
            System.out.println("module inserted......");
        } catch (SQLException e) {
            System.out.println("module already exist......");
        }
    }

    public void updateSelected(int sid, HashMap<String, Object> map){
        try {

            String sql= "UPDATE `module` " +
                        "SET mMath      = '" + map.get("Math"       ) + "' ," +
                        "    mPhysics   = '" + map.get("Physics"    ) + "' ," +
                        "    mChemistry = '" + map.get("Chemistry"  ) + "' ," +
                        "    mEnglish   = '" + map.get("English"    ) + "' ," +
                        "    mHistory   = '" + map.get("History"    ) + "' ," +
                        "    mBiology   = '" + map.get("Biology"    ) + "' ," +
                        "    mGeography = '" + map.get("Geography"  ) + "'  " +
                        "WHERE m_sid = " + sid;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("module updated......");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
