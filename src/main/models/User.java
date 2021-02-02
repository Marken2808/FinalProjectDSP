package main.models;

import java.io.Serializable;
import java.sql.SQLException;

public class User {

    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


//    ------------------------------------------------

    public void insertTeacherData(){

    }

    public void insertStudentData(Student student, Face face) throws SQLException {

        new StudentDAO().insert(student);
        new ModuleDAO().insert(student);
        new FaceDAO().insert(face);
    }

}
