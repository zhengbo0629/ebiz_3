package com.ebiz.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class ExecuteTimePrintAspect {
    private final static Logger logger = LoggerFactory.getLogger(ExecuteTimePrintAspect.class);
    @Value("${MAX_TIME_OUT}")
    private int MAX_TIME_OUT;

    @Around(value = "execution(* com.ebiz.controller..*(..))")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        String target = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        logger.warn("AOP执行时间 " + target + "的" + methodName + "()执行" + ((executeTime > MAX_TIME_OUT) ? "时间过长," : "") + "耗时" + executeTime + "毫秒");
        return result;
    }
}
