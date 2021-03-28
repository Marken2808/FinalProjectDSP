package utils;

import java.sql.*;

public class DBbean {

    public static Connection conn;
    public static PreparedStatement pstmt;

    public static Connection getConnection() {
//        jdbc:mysql://localhost:3306/schoolmana?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
        String mysqlUrl = "jdbc:mysql://localhost:3306/schoolmana?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(mysqlUrl, "root", "123456");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established......");
        return null;
    }

    public static PreparedStatement getPreparedStatement() {
        return pstmt;
    }


    public static boolean isIdMark(int sid){
        try {
            pstmt = conn.prepareStatement(
                    "select  m_sId from `module` where \n" +
                            "mMath is null or \n" +
                            "mPhysics is null or \n" +
                            "mChemistry is null or\n" +
                            "mEnglish is null or\n" +
                            "mHistory is null or\n" +
                            "mBiology is null or\n" +
                            "mGeography is null"
            );
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                if(sid == rs.getInt(1)){
                    return false;
                }
            }
//            System.out.println("checked id null mark......");
        } catch (SQLException e) {
            System.out.println("cannot access null mark table......");
        }
        return true;
    }

}

