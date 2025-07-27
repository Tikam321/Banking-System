package com.tikam.bankingSystem.CustomInterceptor;

import com.tikam.bankingSystem.annotation.CustomAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class AnnotationBasedCustomInterceptor {

    @Around("@annotation(com.tikam.bankingSystem.annotation.CustomAnnotation)") // pointcut expression
    public  void invoke(ProceedingJoinPoint joinPoint) throws Throwable { // advice
        System.out.println("do something before actual method");

        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        if (method.isAnnotationPresent(CustomAnnotation.class)) {
            CustomAnnotation annotation = method.getAnnotation(CustomAnnotation.class);
            System.out.println("name from the annotation " + annotation.key());
        }

        joinPoint.proceed();
        System.out.println("do somethif after actual method.");
    }
}
