package com.mapache.Enotes_API_Service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

//    @Before("execution(* com.mapache.Enotes_API_Service.controller..*(..))")
//    public void beforeController(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String className = signature.getDeclaringType().getSimpleName();
//        String methodName = signature.getName();
//        log.info("Calling :: {} :: {}()", className, methodName);
//    }
//
//    @After("execution(* com.mapache.Enotes_API_Service.controller..*(..))")
//    public void afterController(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String className = signature.getDeclaringType().getSimpleName();
//        String methodName = signature.getName();
//        log.info("End Calling :: {} :: {}()", className, methodName);
//    }

    @Around(value = "execution(* com.mapache.Enotes_API_Service.controller..*(..))")
    public Object jointPointController(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Star Calling :: {} :: {}()", className, methodName);
        Object result = joinPoint.proceed();
        log.info("End Calling :: {} :: {}()", className, methodName);
        return result;
    }

    @Around(value = "execution(* com.mapache.Enotes_API_Service.service..*(..))")
    public Object jointPointService(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Star Calling :: {} :: {}()", className, methodName);
        Object result = joinPoint.proceed();
        log.info("End Calling :: {} :: {}()", className, methodName);
        return result;
    }


}
