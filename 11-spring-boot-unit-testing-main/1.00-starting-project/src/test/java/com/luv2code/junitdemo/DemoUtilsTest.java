package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
        System.out.println("@BefoeEach executes before the execution of each test method");
    }

    @AfterEach
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
    }

    @Test
    void testEqualsAndNotEquals() {
        System.out.println("Running test: testEqualsAndNotEquals()");


        assertEquals(6, demoUtils.add(2, 4), "2 + 4 should equal 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1 + 9 should not equal 6");
    }

    @Test
    void testNullAndNotNull() {
        System.out.println("Running test: testNullAndNotNull()");

        String str1 = null;
        String str2 = "lovee";

        assertNull(demoUtils.checkNull(str1), "Should be null");
        assertNotNull(demoUtils.checkNull(str2), "Shoud be not null");
    }
}
