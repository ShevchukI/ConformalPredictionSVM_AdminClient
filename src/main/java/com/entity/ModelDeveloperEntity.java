package com.entity;

public class ModelDeveloperEntity {
    private int id;
    private String name;
    private String surname;
    private String telephone;
    private String email;
    private EmployeeStatusEntity employeeStatus;

    public ModelDeveloperEntity(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeStatusEntity getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatusEntity employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
}
