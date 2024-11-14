package com.example.tp6;

public class Student {
    private int Id;
    private String name;
    private Double grade;
    public Student(){
    }
    public Student(int Id,String name,Double grade){
        this.Id=Id;
        this.name=name;
        this.grade=grade;
    }
    public String getName() {
        return name;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}

