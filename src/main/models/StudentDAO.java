package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static utils.DBbean.isIdMark;

public class StudentDAO {

    private Connection conn;
    private PreparedStatement pstmt;

    public StudentDAO() {
        this.conn = DBbean.getConnection();
        this.pstmt = DBbean.getPreparedStatement();
    }

    public ObservableList<Student> showStudentTable(){
        ObservableList<Student> studentLists = FXCollections.observableArrayList();
        try {
            pstmt = conn.prepareStatement("Select * from Student");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                studentLists.add(new Student(
                        rs.getInt("sId"),
                        rs.getString("sName"),
                        isIdMark(rs.getInt("sId")),
                        new Attendance().getLast5days(rs.getInt("sId"))
                ));
            }
//            System.out.println("accessed successfully");
        } catch (SQLException throwables) {
            System.out.println("cannot access student table");
        }
        return studentLists;
    }

    public ArrayList<Student> select(String query){
        ArrayList<Student> students = new ArrayList<>();
        try {
//            pstmt = conn.prepareStatement("Select * from Student");
            pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                students.add(
                        new Student(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
//            System.out.println("student retrieved......");
            return students;

        } catch (SQLException e) {}
        return null;
    }

    public Student retrieveStudentByID(int sid){
        ArrayList<Student> test = select("Select * from Student where sid = "+sid);
        return test.get(0);
    }

    public Student retrieve(int userID) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM student WHERE s_uId = " + userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return  new Student(
                        rs.getInt("sId"),
                        rs.getString("sName"),
                        rs.getInt("s_uId")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Student student) {

        try {
            pstmt = conn.prepareStatement("INSERT INTO student (sId, s_uId) VALUES(?, ?)");
            pstmt.setInt(1, 0);
//            pstmt.setString(2, student.getStudentName());
            pstmt.setInt(2, student.getUserID());
            //Executing the statement
            pstmt.execute();
            System.out.println("student inserted......");
        } catch (SQLException e) {
            System.out.println("student already exist......");
        }

    }

    // update if selected student
    public void updateSelected(int sid, HashMap<String, Object> map){
        try {
            String sql = "UPDATE student SET sname = '" + map.get("Student Name").toString().toUpperCase() + "' WHERE sid = " + sid;
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.executeUpdate();
            System.out.println("student updated......");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update when you are student
    public void update(Student student){
        try {
            String sql = "UPDATE student SET sname = '" + student.getStudentName() + "' WHERE sid = " + student.getStudentId();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.executeUpdate();
            System.out.println("student updated......");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
