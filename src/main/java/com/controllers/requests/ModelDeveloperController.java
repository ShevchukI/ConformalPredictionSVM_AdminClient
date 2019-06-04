package com.controllers.requests;

import com.entity.ModelDeveloperEntity;
import com.google.gson.Gson;
import com.tools.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class ModelDeveloperController extends MainController {

    public static HttpResponse createModelDeveloper(ModelDeveloperEntity modelDeveloperEntity) {
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

    public static HttpResponse changeModelDeveloper(ModelDeveloperEntity modelDeveloperEntity) {
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

    public static HttpResponse getModelDeveloperPage(int pageIndex) {
        String url = getUrl() + "/specialist/all/" + pageIndex + "/" + Constant.getObjectOnPage();
        HttpGet request = new HttpGet(url);
        HttpResponse response = crudEntity(null, null, request, null, null);
        return response;
    }

    public static HttpResponse deleteModelDeveloper(int id) {
        String url = getUrl() + "/specialist/" + id;
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = crudEntity(null, null, null, null, request);
        return response;
    }
}
