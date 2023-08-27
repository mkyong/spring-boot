package com.mkyong.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Value("${db.name:hello}")
    private String name; // if db.name doesn't exist, we get the default hello

    @Value("${db.thread-pool:3}")
    private Integer threadPool; // if db.thread-pool doesn't exist, we get the default 3

    @Override
    public String toString() {
        return "DatabaseService{" +
                "name='" + name + '\'' +
                ", threadPool=" + threadPool +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(Integer threadPool) {
        this.threadPool = threadPool;
    }
}
