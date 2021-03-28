package models;

import java.util.Arrays;

public class Module {
    private int moduleID;
    private int studentID;
    private Subject[] subjects;

    public Module( int moduleID, Subject[] subjects, int studentID) {
        this.moduleID = moduleID;
        this.subjects = subjects;
        this.studentID = studentID;
    }

    public int getModuleID() {
        return moduleID;
    }

//    public void setModuleID(int moduleID) {
//        this.moduleID = moduleID;
//    }

    public Subject[] getSubjects() {
        return subjects;
    }

//    public void setSubjects(Subject[] subjects) {
//        this.subjects = subjects;
//    }

    public int getStudentID() {
        return studentID;
    }

//    public void setStudentID(int studentID) {
//        this.studentID = studentID;
//    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleID=" + moduleID +
                ", studentID=" + studentID +
                ", subjects=" + Arrays.toString(subjects) +
                '}';
    }
}