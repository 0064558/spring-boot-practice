package com.luv2code.springboot.thymeleafdemo.aspect;

import jakarta.persistence.Column;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {
    // setup logger
    private Logger logger = Logger.getLogger(getClass().getName());


}
