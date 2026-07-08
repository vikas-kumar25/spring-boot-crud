package com.SpringBoot.SpringBootCrudApplication.dto;

import java.time.LocalDateTime;

/**********************************************
 Developer Name: Vikas
 Created on: 08-07-2026 00:24
 Project Name: SpringBootCrudApplication ${
 /**********************************************/

//Here i am not updating Email ids.

public class UpdateStudentRequestDto {

    private String name;
    private int age;
    private int rollNo;
    private String subject;
    private Boolean deleted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
