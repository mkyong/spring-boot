package com.mkyong.reactive.services;

public class DefaultHelloService implements HelloService {
    @Override
    public String get() {
        return "Hello Junit 5";
    }
}
