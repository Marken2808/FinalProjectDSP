package main.models;

import java.util.UUID;

public class Users {

    private String usersName;
    private String usersIP;
    private String usersServerIP;
    private String usersTime;

    public Users(String usersName, String usersIP, String usersServerIP, String usersTime) {
        this.usersName = usersName;
        this.usersIP = usersIP;
        this.usersServerIP = usersServerIP;
        this.usersTime = usersTime;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersIP() {
        return usersIP;
    }

    public void setUsersIP(String usersIP) {
        this.usersIP = usersIP;
    }

    public String getUsersServerIP() {
        return usersServerIP;
    }

    public void setUsersServerIP(String usersServerIP) {
        this.usersServerIP = usersServerIP;
    }

    public String getUsersTime() {
        return usersTime;
    }

    public void setUsersTime(String usersTime) {
        this.usersTime = usersTime;
    }
}
