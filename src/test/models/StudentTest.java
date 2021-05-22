package models;

import org.junit.jupiter.api.*;


class StudentTest {

    Student testStudent;

    @BeforeAll
    static void beforeAll() {
        System.out.println("*** Before All ***");
    }

    @BeforeEach
    void setUp() {
        System.out.println(">>> Start Test >>>");
        testStudent = new Student(888, "TuanNguyen", 1010);
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
    void getStudentId() {
        int testStudentID = 888;
        Assertions.assertEquals(testStudent.getStudentId(), testStudentID);
        System.out.println("Testing with '" + testStudentID + "' PASSED");
    }

    @Test
    void getStudentName() {
        String testStudentName = "TuanNguyen";
        Assertions.assertEquals(testStudent.getStudentName(), testStudentName);
        System.out.println("Testing with '" + testStudentName + "' PASSED");
    }

    @Test
    void isStudentMarked() {
        boolean testStudentMarked = false;
        Assertions.assertEquals(testStudent.isStudentMarked(), testStudentMarked);
        System.out.println("Testing with '" + testStudentMarked + "' PASSED");
    }

    @Test
    void getStudentUserId() {
        int testStudentUserID = 1010;
        Assertions.assertEquals(testStudent.getStudentUserId(), testStudentUserID);
        System.out.println("Testing with '" + testStudentUserID + "' PASSED");
    }


}