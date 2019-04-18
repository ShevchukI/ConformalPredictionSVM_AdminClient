package com.controllers.requests;

import com.entity.DoctorEntity;
import com.google.gson.Gson;
import com.tools.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class DoctorController extends MainController{
    public static HttpResponse createDoctor(DoctorEntity doctorEntity, int specializationId, int employeeStatusId) {
        String json = new Gson().toJson(doctorEntity);
        String url = getUrl() + "/doctor/" + specializationId + "/" + employeeStatusId;
        HttpPost request = new HttpPost(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), request, null, null, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse changeDoctor(DoctorEntity doctorEntity, int specializationId, int employeeStatusId) {
        String json = new Gson().toJson(doctorEntity);
        String url = getUrl() + "/doctor/" + doctorEntity.getId() + "/" + specializationId + "/" + employeeStatusId;
        HttpPut request = new HttpPut(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), null, null, request, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse getDoctorPage(int pageIndex) {
        String url = getUrl() + "/doctor/all/" + pageIndex + "/" + Constant.getObjectOnPage();
        HttpGet request = new HttpGet(url);
        HttpResponse response = crudEntity(null, null, request, null, null);
        return response;
    }
}
