package models;

import org.junit.jupiter.api.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {

    User testUser;

    @BeforeAll
    static void beforeAll() {
        System.out.println("*** Before All ***");
    }

    @BeforeEach
    void setUp() {
        System.out.println(">>> Start Test >>>");
        testUser = new User("admin", "pass123");
        testUser.setUserID(111);
        testUser.setStatus("Active");
        testUser.setRole("ADMIN");
    }

    @Test
    void getUsername() {
        String testUsername = "admin";
        Assertions.assertEquals(testUser.getUsername(), testUsername);
        System.out.println("Testing with '" + testUsername + "' PASSED");
    }

    @Test
    void getPassword() {
        String testPassword = "pass123";
        Assertions.assertEquals(testUser.getPassword(), testPassword);
        System.out.println("Testing with '" + testPassword + "' PASSED");
    }


    @Test
    void getUserID() {
        int testUserID = 111;
        Assertions.assertEquals(testUser.getUserID(), testUserID);
        System.out.println("Testing with '" + testUserID + "' PASSED");
    }

    @Test
    void getStatus() {
        String testStatus = "Active";
        Assertions.assertEquals(testUser.getStatus(), testStatus);
        System.out.println("Testing with '" + testStatus + "' PASSED");
    }

    @Test
    void getRole() {
        String testRole = "ADMIN";
        Assertions.assertEquals(testUser.getRole(),testRole);
        System.out.println("Testing with '" + testRole + "' PASSED");
    }

    @AfterEach
    void tearDown() {
        System.out.println("~~~ End Test ~~~");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("*** After All ***");
    }


}