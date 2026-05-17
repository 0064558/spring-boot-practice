package com.example.springcoredemo.common;

import org.springframework.stereotype.Component;

@Component // marks the class as a Spring Bean for dependency injection
public class BaseballCoach implements Coach {

    public BaseballCoach() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Spend 30 minutes in batting practice";
    }
}
