package com.college.resources.model;

public class Student {
    private static int nextId = 1;

    private final int id;
    private String name;
    private String department;
    private String email;
    private String phone;

    public Student(String name, String department, String email, String phone) {
        this.id = nextId++;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + '\'' + ", dept='" + department + "'}";
    }
}
