package com.mkyong.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-profile-specific-properties
// Spring boot, default profile is 'default'
@Service
@Profile({"sunny", "default"})
public class SunnyDayService implements WeatherService {

    @Override
    public String forecast() {
        return "Today is sunny day!";
    }

}