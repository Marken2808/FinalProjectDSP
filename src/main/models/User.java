package main.models;

import java.sql.SQLException;

public class User {

    public void insertTeacherData(){

    }

    public void insertStudentData(Student student, Face face) throws SQLException {

        new StudentDAO().insert(student);
        new ModuleDAO().insert(student);
        new FaceDAO().insert(face);
    }

}
