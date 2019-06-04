package com.entity;

import com.controllers.requests.DoctorController;
import com.tools.Constant;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import org.apache.http.HttpResponse;

import java.io.IOException;

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
        HttpResponse response = DoctorController.createDoctor(this);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 201) {
            String[] content = getContent(response);

            TextArea textArea = new TextArea("Login: " + content[1] + "\nPassword: " + content[2]);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(300);
            textArea.setMaxHeight(100);
            GridPane gridPane = new GridPane();
            gridPane.add(textArea, 0, 0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText("Doctor saved!");
            alert.getDialogPane().setContent(gridPane);
            alert.showAndWait();
        }
    }

    private String[] getContent(HttpResponse response) {
        String[] strings = new String[10];
        String[] content = new String[3];
        try {
            strings = Constant.responseToString(response).split("\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] login = strings[3].split("_");
        content[0] = login[2];
        content[1] = strings[3];
        content[2] = strings[7];
        return content;
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
