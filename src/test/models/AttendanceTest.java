package models;

import org.junit.jupiter.api.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceTest {

    Attendance testAttendance;

    @BeforeAll
    static void beforeAll() {
        System.out.println("*** Before All ***");
    }

    @BeforeEach
    void setUp() {
        System.out.println(">>> Start Test >>>");
        testAttendance = new Attendance(Date.valueOf(LocalDate.now()),"P",111);
    }

    @AfterEach
    void tearDown() {
        System.out.println("~~~ End Test ~~~");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("*** After All ***");
    }

    @Test
    void getAttDate() {
        Date testAttendanceDate = Date.valueOf(LocalDate.now());
        Assertions.assertEquals(testAttendance.getAttDate(), testAttendanceDate);
        System.out.println("Testing with '" + testAttendanceDate + "' PASSED");
    }

    @Test
    void getAttStatus() {
        String testAttendanceStatus = "P";
        Assertions.assertEquals(testAttendance.getAttStatus(), testAttendanceStatus);
        System.out.println("Testing with '" + testAttendanceStatus + "' PASSED");
    }

    @Test
    void getStudentID() {
        int testStudentId = 111;
        Assertions.assertEquals(testAttendance.getStudentID(), testStudentId);
        System.out.println("Testing with '" + testStudentId + "' PASSED");
    }

    @Test
    void getLast5days() {
//        ArrayList<LocalDate> last5days = new ArrayList<>();
    }

    @Test
    void getAllDate() {
    }
}