package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
        System.out.println("@BeforeEach executes before the execution of each test method");
    }

    @Test
    @DisplayName("Equals and Not Equals")
    void testEqualsAndNotEquals() {
        assertEquals(6, demoUtils.add(2, 4), "2 + 4 should equal 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1 + 9 should not equal 6");
    }

    @Test
    @DisplayName("Null And Not Null")
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
    void testTrueFalse() {
        int n1 = 10;
        int n2 = 20;

        assertTrue(demoUtils.isGreater(n2, n1), "This should return true");
        assertFalse(demoUtils.isGreater(n1, n2), "This should return false");
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
