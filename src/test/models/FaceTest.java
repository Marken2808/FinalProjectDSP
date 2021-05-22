package models;

import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class FaceTest {

    Face testFace;

    @BeforeAll
    static void beforeAll() {
        System.out.println("*** Before All ***");
    }

    @BeforeEach
    void setUp() throws FileNotFoundException {
        System.out.println(">>> Start Test >>>");
        testFace = new Face(new FileInputStream("src/resources/images/background/bg.png"),123,new Student(111,"tuan"));
        testFace.setFaceID(333);
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
    void getFaceID() {
        int testFaceID = 333;
        Assertions.assertEquals(testFace.getFaceID(), testFaceID);
        System.out.println("Testing with '" + testFaceID + "' PASSED");
    }

    @Test
    void getFaceData() throws FileNotFoundException {
//        InputStream testFaceData = new FileInputStream("src/resources/images/background/bg.png");
//        Assertions.assertEquals(testFace.getFaceData(), testFaceData);
//        System.out.println("Testing with '" + testFaceData + "' PASSED");
    }

    @Test
    void getFaceSet() {
        int testFaceSet = 123;
        Assertions.assertEquals(testFace.getFaceSet(), testFaceSet);
        System.out.println("Testing with '" + testFaceSet + "' PASSED");
    }

    @Test
    void getStudent() {
    }
}