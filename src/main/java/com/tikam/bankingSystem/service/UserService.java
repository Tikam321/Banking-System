package com.tikam.bankingSystem.service;

import com.tikam.bankingSystem.annotation.CustomAnnotation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Async("myThreadPoolExecutor")
    public void asyncMethod() {
        System.out.println("inside the asyncMethod " + Thread.currentThread().getName());
        try{
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // this is custom annotation
    @CustomAnnotation(key = "user")
    public void getUser() {
        System.out.println("get user detail");
    }
}
