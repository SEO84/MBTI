package com.sample.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Around("execution(* com.sample.controller..*(..))")
    public Object controllerLogger(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Start: {}.{}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        Object result = pjp.proceed();
        log.debug("End: {}.{}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        return result;
    }
}
