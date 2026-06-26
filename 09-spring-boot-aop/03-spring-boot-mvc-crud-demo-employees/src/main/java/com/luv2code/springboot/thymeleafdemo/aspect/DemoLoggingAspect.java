package com.luv2code.springboot.thymeleafdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {
    // setup logger
    private Logger logger = Logger.getLogger(getClass().getName());

    // setup pointcut declarations
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..))")
    private void forControllerPackage() {}

    // do the same for service and dao
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..))")
    private void forDaoPackage() {}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {}

    // add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        // display the method we are calling
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====> in @Before: calling method: " + method);

        // display the arguments to the method
        Object[] args = joinPoint.getArgs();

        for (Object tempArg : args) {
            logger.info("=====> argument: " + tempArg);
        }
    }

    // add @AfterReturning advice
    @AfterReturning(pointcut = "forAppFlow()",
            returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        // display the method we are turning from
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====> in @AfterReturning: calling method: " + method);

        // display data returned
        logger.info("=====> result: " + result);

    }
}
