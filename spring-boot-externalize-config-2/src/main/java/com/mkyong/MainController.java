package com.mkyong;

import com.mkyong.global.EmailServer;
import com.mkyong.global.EmailServer2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private EmailServer email;

    @Autowired
    private EmailServer2 email2;

    @GetMapping("/email")
    public EmailServer getEmail() {
        return email;
    }

    @GetMapping("/email2")
    public EmailServer2 getEmail2() {
        return email2;
    }

}