package com.sample.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AdviceAspect {
    @Around("execution(* com.sample.advice..*(..))")
    public Object adviceController(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Entering: {}.{}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        Object result = pjp.proceed();
        log.debug("Exiting: {}.{}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        return result;
    }
}
