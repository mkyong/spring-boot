package com.mkyong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService {

    @Value("${db.name:hello}")
    private String name; // if db.name doesn't exist, we get hello

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
