package models;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    Subject testSubject;

    @BeforeAll
    static void beforeAll() {
        System.out.println("*** Before All ***");
    }

    @BeforeEach
    void setUp() {
        System.out.println(">>> Start Test >>>");
        testSubject = new Subject("Math", 5.5);
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
    void getSubjectName() {
        String testSubjectName = "Math";
        Assertions.assertEquals(testSubject.getSubjectName(), testSubjectName);
        System.out.println("Testing with '" + testSubjectName + "' PASSED");
    }

    @Test
    void getSubjectMark() {
        double testSubjactMark = 5.5;
        Assertions.assertEquals(testSubject.getSubjectMark(), testSubjactMark);
        System.out.println("Testing with '" + testSubjactMark + "' PASSED");
    }
}