package com.controllers.requests;

import com.entity.DoctorEntity;
import com.google.gson.Gson;
import com.tools.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import static com.controllers.requests.MainController.crudEntity;
import static com.controllers.requests.MainController.getUrl;

public class DoctorController {
    public static HttpResponse createDoctor(DoctorEntity doctorEntity, int specializationId, int employeeStatusId) {
        String json = new Gson().toJson(doctorEntity);
        String url = getUrl() + "/doctor/" + specializationId + "/" + employeeStatusId;
        HttpPost request = new HttpPost(url);
        HttpResponse response = crudEntity(json, request, null, null, null);
        return response;
    }

    public static HttpResponse changeDoctor(DoctorEntity doctorEntity, int specializationId, int employeeStatusId) {
        String json = new Gson().toJson(doctorEntity);
        String url = getUrl() + "/doctor/" + doctorEntity.getId() + "/" + specializationId + "/" + employeeStatusId;
        HttpPut request = new HttpPut(url);
        HttpResponse response = crudEntity(json, null, null, request, null);
        return response;
    }

    public static HttpResponse getDoctorPage(int pageIndex) {
        String url = getUrl() + "/doctor/all/" + pageIndex + "/" + Constant.getObjectOnPage();
        HttpGet request = new HttpGet(url);
        HttpResponse response = crudEntity(null, null, request, null, null);
        return response;
    }
}
