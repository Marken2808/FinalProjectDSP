package models;

import org.junit.jupiter.api.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {

    User testUser;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Each Start Test");
        testUser = new User("admin", "pass");
    }

    @Test
//    @DisplayName("Not create when username is null")
    void getUsername() {
        Assertions.assertEquals("admin", testUser.getUsername());
//        Assertions.assertThrows(RuntimeException.class, ()->{
//           testUser.setUsername(null);
//        });
    }

    @Test
    void getPassword() {
        Assertions.assertEquals("pass", testUser.getPassword());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Each End Test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }
}