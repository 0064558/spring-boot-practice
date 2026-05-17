package com.example.springcoredemo.common;

import org.springframework.stereotype.Component;

@Component // marks the class as a Spring Bean for dependency injection
public class TrackCoach implements Coach {
    @Override
    public String getDailyWorkout() {
        return "Run a hard 5K!";
    }
}
