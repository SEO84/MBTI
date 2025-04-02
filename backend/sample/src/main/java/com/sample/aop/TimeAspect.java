package com.sample.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class TimeAspect {
    @Around("execution(* com.sample.controller..*(..))")
    public Object timerController(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long totalTime = System.currentTimeMillis() - startTime;
        log.info("{}.{} took {} ms", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), totalTime);
        return result;
    }
}
