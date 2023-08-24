package com.mkyong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/server/file.properties")
public class FileProperties {

    @Value("${file.path}")
    private String path;

    @Override
    public String toString() {
        return "FileProperties{" +
                "path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
