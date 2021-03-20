package main.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public UserDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }


//    ------------------------------------------------

    public ObservableList<User> showUserTable(){
        ObservableList<User> userLists = FXCollections.observableArrayList();
        try {
            pstmt = conn.prepareStatement("Select * from User");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                userLists.add(new User(
                        rs.getInt("uId"),
                        rs.getString("uUsername"),
                        rs.getString("uPassword"),
                        rs.getString("uStatus"),
                        rs.getString("uRole")
                ));
            }
//            System.out.println("accessed successfully");
        } catch (SQLException throwables) {
            System.out.println("cannot access user table");
        }
        return userLists;
    }

    public boolean authenticate (User user) {

        try {
            pstmt = conn.prepareStatement("SELECT * FROM user " +
                    "WHERE uUsername= '" + user.getUsername() + "'" +
                    "AND uPassword= '" + user.getPassword() +"'" +
                    "AND uStatus= 'Active'");
            return pstmt.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("No user found");
        }
        return false;
    }

    public void insert(User user){
        try {
            pstmt = conn.prepareStatement("INSERT INTO user (uUsername, uPassword) VALUES(?,?)");
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());

            //Executing the statement
            pstmt.execute();
            System.out.println("user inserted......");
        } catch (SQLException e) {
            System.out.println("user already exist......");

        }
    }

//    public void update(int uid){
//        try {
//
//            String sql= "UPDATE `module` " +
//                    "SET mMath      = '" + map.get("Math"       ) + "' ," +
//                    "    mPhysics   = '" + map.get("Physics"    ) + "' ," +
//                    "    mChemistry = '" + map.get("Chemistry"  ) + "' ," +
//                    "    mEnglish   = '" + map.get("English"    ) + "' ," +
//                    "    mHistory   = '" + map.get("History"    ) + "' ," +
//                    "    mBiology   = '" + map.get("Biology"    ) + "' ," +
//                    "    mGeography = '" + map.get("Geography"  ) + "'  " +
//                    "WHERE m_sid = " + sid;
//            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.executeUpdate();
//            System.out.println("module updated......");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }














//    -----------------------------------
    public void insertTeacherData(){

    }

    public void insertStudentData(Student student, Face face) throws SQLException {

        new StudentDAO().insert(student);
        new FaceDAO().insert(face);
        new ModuleDAO().insert(student);
        //temp
//        new AttendanceDAO().insert(new Attendance("P",student.getStudentId()));
    }

}
