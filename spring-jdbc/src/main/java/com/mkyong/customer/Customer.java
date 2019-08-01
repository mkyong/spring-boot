package com.mkyong.customer;

import java.time.LocalDateTime;

public class Customer {

    private Long ID;
    private String name;
    private Integer age;
    private LocalDateTime createdDate;

    public Customer() {
    }

    public Customer(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Customer(Long ID, String name, Integer age, LocalDateTime createdDate) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createdDate=" + createdDate +
                '}';
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
