package com.mkyong.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailServer {

    @Value("${email.smtp.server}")
    private String server;

    @Value("${email.smtp.port}")
    private Integer port;

    @Value("${email.smtp.username}")
    private String username;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
