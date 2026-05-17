package com.example.springcoredemo.common;

import org.springframework.stereotype.Component;

@Component // marks the class as a Spring Bean for dependency injection
public class TennisCoach implements Coach {

    public TennisCoach() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Practice your backhand volley";
    }
}
