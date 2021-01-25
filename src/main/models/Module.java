package main.models;

public class Module {
    private int moduleID;
    private Subject[] subjects;
    private Student student;

    private Subject modMaths;
    private Subject modPhysics;
    private Subject modChemistry;
    private Subject modLiterature;
    private Subject[] modLanguages;     // English, French, Spanish, Italian, ...
    private Subject modHistory;
    private Subject modBiology;
    private Subject modGeography;

    public Module(int moduleID, Subject[] subjects, Student student) {
        this.moduleID = moduleID;
        this.subjects = subjects;
        this.student = student;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public Subject[] getSubjects() {
        return subjects;
    }

    public void setSubjects(Subject[] subjects) {
        this.subjects = subjects;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getModMaths() {
        return modMaths;
    }

    public void setModMaths(Subject modMaths) {
        this.modMaths = modMaths;
    }

    public Subject getModPhysics() {
        return modPhysics;
    }

    public void setModPhysics(Subject modPhysics) {
        this.modPhysics = modPhysics;
    }

    public Subject getModChemistry() {
        return modChemistry;
    }

    public void setModChemistry(Subject modChemistry) {
        this.modChemistry = modChemistry;
    }

    public Subject getModLiterature() {
        return modLiterature;
    }

    public void setModLiterature(Subject modLiterature) {
        this.modLiterature = modLiterature;
    }

    public Subject[] getModLanguages() {
        return modLanguages;
    }

    public void setModLanguages(Subject[] modLanguages) {
        this.modLanguages = modLanguages;
    }

    public Subject getModHistory() {
        return modHistory;
    }

    public void setModHistory(Subject modHistory) {
        this.modHistory = modHistory;
    }

    public Subject getModBiology() {
        return modBiology;
    }

    public void setModBiology(Subject modBiology) {
        this.modBiology = modBiology;
    }

    public Subject getModGeography() {
        return modGeography;
    }

    public void setModGeography(Subject modGeography) {
        this.modGeography = modGeography;
    }
}
