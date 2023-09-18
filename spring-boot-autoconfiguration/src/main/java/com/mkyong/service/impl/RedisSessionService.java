package com.mkyong.service.impl;

import com.mkyong.service.WebSessionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.feature.new", havingValue = "true")
public class RedisSessionService implements WebSessionService {

    @Override
    public String getUserData() {
        return "Data from Redis...";
    }
}
