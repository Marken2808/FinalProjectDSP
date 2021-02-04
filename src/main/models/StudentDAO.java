package main.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.utils.DBbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static main.utils.DBbean.isIdMark;

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
                students.add(new Student(rs.getInt(1), rs.getString(2)));
            }
            System.out.println("student retrieved......");
            return students;

        } catch (SQLException e) {
            System.out.println("error......");
        }
        return null;
    }

    public Student retrieveStudentByID(int sid){
        ArrayList<Student> test = select("Select * from Student where sid = "+sid);
        return test.get(0);
    }

    public void insert(Student student) {

        try {
            pstmt = conn.prepareStatement("INSERT INTO student (sId,sName) VALUES(?,?)");
            pstmt.setInt(1, student.getStudentId());
            pstmt.setString(2, student.getStudentName());
            //Executing the statement
            pstmt.execute();
            System.out.println("student inserted......");
//            insertModule(student.getStudentId());
        } catch (SQLException e) {
            System.out.println("student already exist......");
        }

    }

    public void update(int sid, HashMap<String, Object> map){
        try {

            String sql = "UPDATE student SET sname = '" + map.get("Student Name") + "' WHERE sid = " + sid;
            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setString(1, "John");

            pst.executeUpdate();
            System.out.println("Updated Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
