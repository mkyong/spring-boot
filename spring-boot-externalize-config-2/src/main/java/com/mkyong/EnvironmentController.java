package com.mkyong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvironmentController {

    @Autowired
    private Environment env;

    @GetMapping("/env")
    public Map<String, String> getEmailFromEnvironment() {

        String smtp = env.getProperty("smtp.server");
        String username = env.getProperty("smtp.username");
        String port = env.getProperty("smtp.port");

        Map<String, String> map = new HashMap<>();
        map.put("smtp", smtp);
        map.put("username", username);
        map.put("port", port);

        return map;
    }

}