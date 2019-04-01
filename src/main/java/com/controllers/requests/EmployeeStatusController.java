package com.controllers.requests;

import com.entity.EmployeeStatusEntity;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import java.io.IOException;

public class EmployeeStatusController extends MainController {

    public static HttpResponse createEmployeeStatus(EmployeeStatusEntity employeeStatusEntity) {
        String json = new Gson().toJson(employeeStatusEntity);
        String url = getUrl() + "/employee-status/";
        HttpPost request = new HttpPost(url);
        HttpResponse response = crudEntity(json, request, null, null, null);
        return response;
    }

    public static HttpResponse getAllEmployeeStatus() {
        String url = getUrl() + "/employee-status/all";
        HttpGet request = new HttpGet(url);
        HttpResponse response = crudEntity(null, null, request, null, null);
        return response;
    }

    public static HttpResponse changeEmployeeStatus(EmployeeStatusEntity employeeStatusEntity) throws IOException {
        String json = new Gson().toJson(employeeStatusEntity);
        String url = getUrl() + "/employee-status/" + employeeStatusEntity.getId();
        HttpPut request = new HttpPut(url);
        HttpResponse response = crudEntity(json, null, null, request, null);
        return response;
    }

    public static HttpResponse deleteEmployeeStatus(int id){
        String url = getUrl()+"/employee-status/"+id;
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = crudEntity(null, null, null, null, request);
        return response;
    }
}
