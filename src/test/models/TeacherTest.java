package models;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    Teacher testTeacher;

    @BeforeAll
    static void beforeAll() {
        System.out.println("*** Before All ***");
    }

    @BeforeEach
    void setUp() {
        System.out.println(">>> Start Test >>>");
        testTeacher = new Teacher(222, "Marken", 111);
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
    void getTeacherId() {
        int testTeacherID = 222;
        Assertions.assertEquals(testTeacher.getTeacherId(), testTeacherID);
        System.out.println("Testing with '" + testTeacherID + "' PASSED");
    }

    @Test
    void getTeacherName() {
        String testTeacherName = "Marken";
        Assertions.assertEquals(testTeacher.getTeacherName(), testTeacherName);
        System.out.println("Testing with '" + testTeacherName + "' PASSED");
    }

    @Test
    void getTeacherUserId() {
        int testTeacherUserID = 111;
        Assertions.assertEquals(testTeacher.getTeacherUserId(), testTeacherUserID);
        System.out.println("Testing with '" + testTeacherUserID + "' PASSED");
    }
}