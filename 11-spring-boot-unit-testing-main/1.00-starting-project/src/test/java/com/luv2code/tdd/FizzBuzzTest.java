package com.luv2code.tdd;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {
    // If number is divisible by 3, print Fizz
    // If number is divisible by 5, print Buzz
    // If number is divisible by 3 and 5, print FizzBuzz
    // If number is NOT divisible by 3 or 5, the print the number

    @DisplayName("Divisible by Three")
    @Test
    @Order(1)
    void testForDivisibleByThree() {
        String expected = "Fizz";

        assertEquals(expected, FizzBuzz.fizzBuzz(3), "Should print Fizz for numbers divisible by 3");
    }

    @DisplayName("Divisible by Five")
    @Test
    @Order(2)
    void testForDivisibleByFive() {
        String expected = "Buzz";

        assertEquals(expected, FizzBuzz.fizzBuzz(5), "Should print Buzz for numbers divisible by 5");
    }

    @DisplayName("Divisible by Three And Five")
    @Test
    @Order(3)
    void testForDivisibleByThreeAndFive() {
        String expected = "FizzBuzz";

        assertEquals(expected, FizzBuzz.fizzBuzz(15), "Should print FizzBuzz for numbers divisible by 3 and 5");
    }

    @DisplayName("NOT Divisible by Three or Five")
    @Test
    @Order(4)
    void testForNotDivisibleByThreeAndFive() {
        String expected = "1";

        assertEquals(expected, FizzBuzz.fizzBuzz(1), "Should print the number for numbers not divisible by 3 or 5");
    }
}
