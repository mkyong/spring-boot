package com.mkyong;

import com.mkyong.global.EmailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private EmailServer email;

    @GetMapping("/email")
    public EmailServer getEmail() {
        return email;
    }


}