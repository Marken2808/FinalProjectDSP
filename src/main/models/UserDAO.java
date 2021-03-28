package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static Connection conn;
    private static PreparedStatement pstmt;

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

//    public void retrieveUserByID(int uid){
//
//        try {
//            pstmt = conn.prepareStatement("Select * from User where uId = " + uid);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()){
//                new User(
//                    rs.getInt("uId"),
//                    rs.getString("uUsername"),
//                    rs.getString("uPassword"),
//                    rs.getString("uStatus"),
//                    rs.getString("uRole")
//                );
//
//            }
//            System.out.println("accessed successfully");
//        } catch (SQLException throwables) {
//            System.out.println("cannot access user table");
//        }
//    }

    public static User authenticate (User user) {

        try {
            pstmt = conn.prepareStatement("SELECT * FROM user " +
                    "WHERE uUsername= '" + user.getUsername() + "'" +
                    "AND uPassword= '" + user.getPassword() +"'" +
                    "AND uStatus= 'Active'");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                return new User(
                    rs.getInt("uId"),
                    rs.getString("uUsername"),
                    rs.getString("uPassword"),
                    rs.getString("uStatus"),
                    rs.getString("uRole")
                );
            }
        } catch (SQLException e) {
            System.out.println("No user found");
        }
        return null;
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

    public void delete(User user){
        try {
            pstmt = conn.prepareStatement("DELETE FROM user where uId = ?");
            pstmt.setInt(1, user.getUserID());
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(User user){
        try {
            String sql= "UPDATE `user` " +
                    "SET uUsername  = '" + user.getUsername() + "' ," +
                    "    uPassword  = '" + user.getPassword() + "' ," +
                    "    uStatus    = '" + user.getStatus()   + "' ," +
                    "    uRole      = '" + user.getRole()     + "'  " +
                    "WHERE uId = " + user.getUserID();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("user updated......");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }














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
