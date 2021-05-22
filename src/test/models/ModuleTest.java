package models;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ModuleTest {

    Module testModule;

    @BeforeAll
    static void beforeAll() {
        System.out.println("*** Before All ***");
    }

    @BeforeEach
    void setUp() {
        System.out.println(">>> Start Test >>>");
        testModule = new Module(10, new Subject[]{new Subject("Physic", 3.5)}, 5);
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
    void getModuleID() {
        int testModuleID = 10;
        Assertions.assertEquals(testModule.getModuleID(), testModuleID);
        System.out.println("Testing with '" + testModuleID + "' PASSED");
    }

    @Test
    void getSubjects() {
//        Subject[] testSubject = new Subject[]{new Subject("Physic",3.5)};
//        Assertions.assertEquals(testModule.getSubjects(), testSubject);
//        System.out.println("Testing with '" + testSubject + "' PASSED");
    }

    @Test
    void getStudentID() {
        int testStudentID = 5;
        Assertions.assertEquals(testModule.getStudentID(), testStudentID);
        System.out.println("Testing with '" + testStudentID + "' PASSED");
    }
}