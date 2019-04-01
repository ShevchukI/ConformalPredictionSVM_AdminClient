package com.controllers.requests;

import com.entity.SpecializationEntity;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import java.io.IOException;

public class SpecializationController extends MainController {

    public static HttpResponse createSpecialization(SpecializationEntity specializationEntity) throws IOException {
        String json = new Gson().toJson(specializationEntity);
        String url = getUrl() + "/specialization/";
        HttpPost request = new HttpPost(url);
        HttpResponse response = crudEntity(json, request, null, null, null);
        return response;
    }

    public static HttpResponse getAllSpecialization() throws IOException {
        String url = getUrl() + "/specialization/all";
        HttpGet request = new HttpGet(url);
        HttpResponse response = crudEntity(null, null, request, null, null);
        return response;
    }

    public static HttpResponse changeSpecialization(SpecializationEntity specializationEntity) throws IOException {
        String json = new Gson().toJson(specializationEntity);
        String url = getUrl() + "/specialization/" + specializationEntity.getId();
        HttpPut request = new HttpPut(url);
        HttpResponse response = crudEntity(json, null, null, request, null);
        return response;
    }

    public static HttpResponse deleteSpecialization(int id) {
        String url = getUrl() + "/specialization/" + id;
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = crudEntity(null, null, null, null, request);
        return response;
    }
}
