package com.ruleEngineWithAST.model;



public class UserData {

    private int age;
    private String department;
    private int salary;
    private int experience;

    public UserData(int age, String department, int salary, int experience) {
        this.age = age;
        this.department = department;
        this.salary = salary;
        this.experience = experience;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
