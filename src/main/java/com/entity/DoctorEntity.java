package com.entity;

public class DoctorEntity {
    private int id;
    private String name;
    private String surname;
    private SpecializationEntity specialization;
    private String telephone;
    private String email;
    private EmployeeStatusEntity employeeStatus;

    public DoctorEntity() {
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

    public SpecializationEntity getSpecializationEntity() {
        return specialization;
    }

    public void setSpecializationEntity(SpecializationEntity specializationEntity) {
        this.specialization = specializationEntity;
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

    public void setEmployeeStatus(EmployeeStatusEntity employeeStatusEntity) {
        this.employeeStatus = employeeStatusEntity;
    }
    public String getVisibleSpecialization(){
        return this.getSpecializationEntity().getName();
    }
}
