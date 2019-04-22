package com.controllers.requests;

import com.entity.SpecializationEntity;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class SpecializationController extends MainController {

    public static HttpResponse createSpecialization(SpecializationEntity specializationEntity) {
        String json = new Gson().toJson(specializationEntity);
        String url = getUrl() + "/specialization/";
        HttpPost request = new HttpPost(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), request, null, null, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse getAllSpecialization(){
        String url = getUrl() + "/specialization/all";
        HttpGet request = new HttpGet(url);
        HttpResponse response = crudEntity(null, null, request, null, null);
        return response;
    }

    public static HttpResponse changeSpecialization(SpecializationEntity specializationEntity){
        String json = new Gson().toJson(specializationEntity);
        String url = getUrl() + "/specialization/" + specializationEntity.getId();
        HttpPut request = new HttpPut(url);
        HttpResponse response = null;
        try {
            response = crudEntity(new StringEntity(json), null, null, request, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse deleteSpecialization(int id) {
        String url = getUrl() + "/specialization/" + id;
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = crudEntity(null, null, null, null, request);
        return response;
    }
}
