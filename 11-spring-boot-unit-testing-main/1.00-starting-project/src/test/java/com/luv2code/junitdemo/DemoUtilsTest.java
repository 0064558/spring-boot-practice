package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
        System.out.println("@BeforeEach executes before the execution of each test method");
    }

    @Test
    @DisplayName("Equals and Not Equals")
    @Order(1)
    void testEqualsAndNotEquals() {
        assertEquals(6, demoUtils.add(2, 4), "2 + 4 should equal 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1 + 9 should not equal 6");
    }

    @Test
    @DisplayName("Null And Not Null")
    @Order(0)
    void testNullAndNotNull() {
        String str1 = null;
        String str2 = "lovee";

        assertNull(demoUtils.checkNull(str1), "Should be null");
        assertNotNull(demoUtils.checkNull(str2), "Shoud be not null");
    }

    @DisplayName("Same and Not Same")
    @Test
    void testSameAndNotSame() {
        String str = "luv2code";

        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Object should refer to same object");
        assertNotSame(str, demoUtils.getAcademy(), "Object should not refer to same object");
    }

    @DisplayName("True and false")
    @Test
    @Order(30)
    void testTrueFalse() {
        int n1 = 10;
        int n2 = 20;

        assertTrue(demoUtils.isGreater(n2, n1), "This should return true");
        assertFalse(demoUtils.isGreater(n1, n2), "This should return false");
    }

    @DisplayName("Array Equals")
    @Test
    void testArrayEquals() {
        String[] stringArray = {"A", "B", "C"};

        assertArrayEquals(stringArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Array should be the same");
    }

    @DisplayName("Iterable Equals")
    @Test
    void testIterableEquals() {
        List<String> list = List.of("luv", "2", "code");

        assertIterableEquals(list, demoUtils.getAcademyInList(), "Expect list should be the same");
    }

    @DisplayName("Lines Match")
    @Test
    @Order(50)
    void testLinesMatch() {
        List<String> list = List.of("luv", "2", "code");

        assertLinesMatch(list, demoUtils.getAcademyInList(), "Lines should match");
    }

    @DisplayName("Throws and Does Not Throw")
    @Test
    void testThrowsAndDoesNotThrow() {
        assertThrows(Exception.class, () -> {demoUtils.throwException(-1);}, "Should throw exception");

        assertDoesNotThrow(() -> {demoUtils.throwException(5);}, "Should not throw exception");
    }

    @DisplayName("Timeout")
    @Test
    void testTimeOut() {
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {demoUtils.checkTimeout();}, "method should execute in 3 seconds");
    }

    @DisplayName("Multiply")
    @Test
    void testMultiply() {
        assertEquals(12, demoUtils.multiply(4, 3), "4*3 must be 12");
    }

    /*@AfterEach
    void tearDownAfterEach() {
        System.out.println("Running @AfterEach");
        System.out.println();
    }

    @BeforeAll
    static void setupBeforeEachClass() {
        System.out.println("@BeforeAll executes only once before all tests method execution in the class");
    }

    @AfterAll
    static void setupAfterEachClass() {
        System.out.println("@AfterAll executes only once after all tests method execution in the class");
    }*/
}
