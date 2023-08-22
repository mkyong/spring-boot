package com.mkyong.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailServer {

    @Value("${smtp.server}")
    private String smtp;

    @Value("${smtp.port}")
    private Integer port;

    @Value("${smtp.username}")
    private String username;

    @Override
    public String toString() {
        return "EmailServer{" +
                "smtp='" + smtp + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                '}';
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
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
