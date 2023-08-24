package com.mkyong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:/server/db.properties")
public class DatabaseProperties {

    @Value("${db.name}")
    private String name;

    // Since Spring Boot 2.?. it supports list of strings from properties file directly
    // if not , register DefaultConversionService
    /**
     *         @Bean
     *         public ConversionService conversionService() {
     *             return new DefaultConversionService();
     *         }
     */
    @Value("${db.cluster.ip}")
    private List<String> clusterIp;

    @Override
    public String toString() {
        return "DatabaseProperties{" +
                "name='" + name + '\'' +
                ", clusterIp=" + clusterIp +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getClusterIp() {
        return clusterIp;
    }

    public void setClusterIp(List<String> clusterIp) {
        this.clusterIp = clusterIp;
    }
}
