package main.models;

import java.sql.SQLException;

public class User {

    private int id;
    private String username;
    private String password;
    private String status;
    private String role;

    public User() {
    }

    public User(int id, String username, String password, String status, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //    ------------------------------------------------
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
