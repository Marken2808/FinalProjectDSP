package models;

public class Subject {

    private String subjectName;
    private double subjectMark;
    private double subjectAvgMark;

    public Subject(String subjectName, double subjectMark) {
        this.subjectName = subjectName;
        this.subjectMark = subjectMark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getSubjectMark() {
        return subjectMark;
    }

    public void setSubjectMark(double subjectMark) {
        this.subjectMark = subjectMark;
    }

//    public double getSubjectAvgMark() {
//        double sum = 0;
//        for(double ele : subjectMark){ sum+=ele; }
//        return subjectAvgMark = sum/subjectMark.length;
//    }


    @Override
    public String toString() {
        return "Subject{" +
                "subjectName='" + subjectName + '\'' +
                ", subjectMark=" + subjectMark +
                "}\n";
    }
}
