package com.entity;

import com.google.gson.Gson;
import com.tools.Constant;
import com.tools.HazelCastMap;
import javafx.scene.control.Alert;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import static com.controllers.requests.MainController.crudEntity;
import static com.controllers.requests.MainController.getUrl;

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

    public void addNew(String name, String surname, String telephone, String email,
                       SpecializationEntity specialization, EmployeeStatusEntity employeeStatus){
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
        this.specialization = specialization;
        this.employeeStatus = employeeStatus;
        HttpResponse response = createDoctor(this);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 201) {
            String[] content = Constant.getContent(response);
            Constant.getTextAreaAlert(null,"Doctor saved!",
                    "Login: " + content[1] + "\nPassword: " + content[2],
                    Alert.AlertType.INFORMATION);
        }
    }

    private HttpResponse createDoctor(DoctorEntity doctorEntity) {
        String json = new Gson().toJson(doctorEntity);
        String url = getUrl() + "/doctor/" + doctorEntity.getSpecializationEntity().getId() + "/" + doctorEntity.getEmployeeStatus().getId();
        HttpPost request = new HttpPost(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), request, null, null, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public int change(String name, String surname, String telephone, String email,
                       SpecializationEntity specialization, EmployeeStatusEntity employeeStatus){

        boolean result = Constant.questionOkCancel("Do you really want to change Doctor "
                +surname + name + "?");
        if (result) {
            this.id = Integer.parseInt(HazelCastMap
                    .getMapByName(HazelCastMap.getMiscellaneousMapName()).get("doctor").toString());
            this.name = name;
            this.surname = surname;
            this.telephone = telephone;
            this.email = email;
            this.specialization = specialization;
            this.employeeStatus = employeeStatus;

            HttpResponse response = changeDoctor(this);
            return response.getStatusLine().getStatusCode();
        }
        return 0;
    }

    private HttpResponse changeDoctor(DoctorEntity doctorEntity) {
        String json = new Gson().toJson(doctorEntity);
        String url = getUrl() + "/doctor/" + doctorEntity.getId() + "/" + doctorEntity.getSpecializationEntity().getId() + "/" + doctorEntity.getEmployeeStatus().getId();
        HttpPut request = new HttpPut(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), null, null, request, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public int deleteDoctor() {
        boolean result = Constant.questionOkCancel("Do you really want to delete Doctor "
                + this.getSurname() + this.getName() + " ?");
        if (result) {
            HttpResponse response = deleteDoctor(this);
            return response.getStatusLine().getStatusCode();
        }
        return 0;
    }

    private HttpResponse deleteDoctor(DoctorEntity doctor) {
        String url = getUrl() + "/doctor/" + doctor.getId();
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = crudEntity(null, null, null, null, request);
        return response;
    }

    public static HttpResponse getDoctorPage(int pageIndex) {
        String url = getUrl() + "/doctor/all/" + pageIndex + "/" + Constant.getObjectOnPage();
        HttpGet request = new HttpGet(url);
        HttpResponse response = crudEntity(null, null, request, null, null);
        return response;
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
