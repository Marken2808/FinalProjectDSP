package main.models;


public class Student {

    private int sid;
    private String sname;
    private boolean marked;

    public Student(int sid, String sname, boolean marked) {
        this.sid = sid;
        this.sname = sname;
        this.marked = marked;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
