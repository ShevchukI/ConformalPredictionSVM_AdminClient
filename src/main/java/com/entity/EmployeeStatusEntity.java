package com.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tools.Constant;
import javafx.scene.control.Alert;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.controllers.requests.MainController.crudEntity;
import static com.controllers.requests.MainController.getUrl;

public class EmployeeStatusEntity {
    private int id;
    private String name;
    private boolean workEnable;

    public EmployeeStatusEntity() {
    }

    public void addNew(String name, boolean workEnable) {
        this.name = name;
        this.workEnable = workEnable;
        HttpResponse response = createEmployeeStatus(this);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 201) {
            try {
                this.id = Integer.parseInt(Constant.responseToString(response));
                Constant.getAlert(null, "Employee status saved!", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int change(String name, boolean workEnable) {
        boolean result = Constant.questionOkCancel("Do you really want to change employee status "
                + name + "?");
        if (result) {
            this.name = name;
            this.workEnable = workEnable;
            HttpResponse response = changeEmployeeStatus(this);
            return response.getStatusLine().getStatusCode();
        } else {
            return 0;
        }
    }

    private HttpResponse changeEmployeeStatus(EmployeeStatusEntity employeeStatusEntity) {
        String json = new Gson().toJson(employeeStatusEntity);
        String url = getUrl() + "/employee-status/" + employeeStatusEntity.getId();
        HttpPut request = new HttpPut(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), null, null, request, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpResponse createEmployeeStatus(EmployeeStatusEntity employeeStatusEntity) {
        String json = new Gson().toJson(employeeStatusEntity);
        String url = getUrl() + "/employee-status/";
        HttpPost request = new HttpPost(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), request, null, null, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public int deleteEmployeeStatus() {
        boolean result = Constant.questionOkCancel("Do you really want to delete Employee Status "
                + this.getName() + " ?");
        if (result) {
            HttpResponse response = deleteEmployeeStatus(this);
            return response.getStatusLine().getStatusCode();
        } else {
            return 0;
        }
    }

    private static HttpResponse deleteEmployeeStatus(EmployeeStatusEntity employeeStatusEntity) {
        String url = getUrl() + "/employee-status/" + employeeStatusEntity.getId();
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = crudEntity(null, null, null, null, request);
        return response;
    }

    public static HttpResponse getAllEmployeeStatus() {
        String url = getUrl() + "/employee-status/all";
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

    public boolean isWorkEnable() {
        return workEnable;
    }

    public void setWorkEnable(boolean workEnable) {
        this.workEnable = workEnable;
    }

    public String getVisibleWorkEnable() {
        if (this.isWorkEnable()) {
            return "Allow";
        } else {
            return "Not allow";
        }
    }

    public static ArrayList<EmployeeStatusEntity> getListFromResponse(HttpResponse response) {
        ArrayList<EmployeeStatusEntity> employeeStatusEntities;
        if (response.getStatusLine().getStatusCode() == 200) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                DataInputStream dataInputStream = new DataInputStream(response.getEntity().getContent());
                String line;
                while ((line = dataInputStream.readLine()) != null) {
                    stringBuilder.append(line);
                }
                dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String json = stringBuilder.toString();
            Gson gson = new Gson();
            Type founderListType = new TypeToken<ArrayList<EmployeeStatusEntity>>() {
            }.getType();
            employeeStatusEntities = gson.fromJson(json, founderListType);
            return employeeStatusEntities;
        } else {
            return null;
        }
    }


}
