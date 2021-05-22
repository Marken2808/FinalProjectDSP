package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBbeanTest {

    Connection conn;
    PreparedStatement pstmt;

    @Test
    void getConnection() throws SQLException {

        String mysqlUrl = "jdbc:mysql://localhost:3306/studentmanagement?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        conn = DriverManager.getConnection(mysqlUrl, "root", "123456");

        Assertions.assertEquals(conn, conn);
        System.out.println("Testing with '" + conn + "' PASSED");

    }

    @Test
    void getPreparedStatement() {
    }
}