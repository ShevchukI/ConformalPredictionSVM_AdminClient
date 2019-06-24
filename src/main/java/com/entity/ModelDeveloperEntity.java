package com.entity;

import com.google.gson.Gson;
import com.tools.Constant;
import com.tools.GlobalMap;
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

public class ModelDeveloperEntity {
    private int id;
    private String name;
    private String surname;
    private String telephone;
    private String email;
    private EmployeeStatusEntity employeeStatus;

    public ModelDeveloperEntity() {
    }

    public void addNew(String name, String surname, String telephone, String email,
                       EmployeeStatusEntity employeeStatus) {
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
        this.employeeStatus = employeeStatus;
        HttpResponse response = createModelDeveloper(this);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 201) {
            String[] content = Constant.getContent(response);
            Constant.getTextAreaAlert(null, "Model developer saved!",
                    "Login: " + content[1] + "\nPassword: " + content[2],
                    Alert.AlertType.INFORMATION);
        }
    }

    public int change(String name, String surname, String telephone, String email,
                      EmployeeStatusEntity employeeStatus) {

        boolean result = Constant.questionOkCancel("Do you really want to change Model Developer "
                + surname + name + "?");
        if (result) {
            this.id = Integer.parseInt(GlobalMap.getMiscMap().get(Constant.getMODELDEVELOPER()));
//            this.id = Integer.parseInt(HazelCastMap
//                    .getMapByName(HazelCastMap.getMiscellaneousMapName()).get("modelDeveloper").toString());
            this.name = name;
            this.surname = surname;
            this.telephone = telephone;
            this.email = email;
            this.employeeStatus = employeeStatus;
            HttpResponse response = changeModelDeveloper(this);
            return response.getStatusLine().getStatusCode();
        } else {
            return 0;
        }
    }

    private HttpResponse createModelDeveloper(ModelDeveloperEntity modelDeveloperEntity) {
        String json = new Gson().toJson(modelDeveloperEntity);
        String url = getUrl() + "/specialist/" + modelDeveloperEntity.getEmployeeStatus().getId();
        HttpPost request = new HttpPost(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), request, null, null, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    private HttpResponse changeModelDeveloper(ModelDeveloperEntity modelDeveloperEntity) {
        String json = new Gson().toJson(modelDeveloperEntity);
        String url = getUrl() + "/specialist/" + modelDeveloperEntity.getId() + "/" + modelDeveloperEntity.getEmployeeStatus().getId();
        HttpPut request = new HttpPut(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), null, null, request, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public int deleteModelDeveloper() {
        boolean result = Constant.questionOkCancel("Do you really want to delete Model Developer "
                + this.getSurname() + this.getName() + " ?");
        if (result) {
            HttpResponse response = deleteModelDeveloper(this);
            return response.getStatusLine().getStatusCode();
        } else {
            return 0;
        }
    }

    private HttpResponse deleteModelDeveloper(ModelDeveloperEntity modelDeveloper) {
        String url = getUrl() + "/specialist/" + modelDeveloper.getId();
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = crudEntity(null, null, null, null, request);
        return response;
    }

    public static HttpResponse getModelDeveloperPage(int pageIndex) {
        String url = getUrl() + "/specialist/all/" + pageIndex + "/" + Constant.getObjectOnPage();
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
