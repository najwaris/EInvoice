package com.moabi.demoeinvoice.service;

import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {
    public int add(int a, int b) {
        return a+b;
    }

    public int mult(int a, int b) {
        return a*b;
    }
}
