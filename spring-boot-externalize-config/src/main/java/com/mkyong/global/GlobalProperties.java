package com.mkyong.global;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

// This component maps value from application.properties to object via @ConfigurationProperties
@Component
@ConfigurationProperties // no prefix, find root level values.
@Validated
public class GlobalProperties {

    // @Value("${thread-pool}")
    // access the value from application.properties via @Value
    @Max(5)
    @Min(0)
    private int threadPool;

    //@Value("${email}")
    @NotEmpty
    private String email;

    public int getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(int threadPool) {
        this.threadPool = threadPool;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "GlobalProperties{" +
                "threadPool=" + threadPool +
                ", email='" + email + '\'' +
                '}';
    }
}
