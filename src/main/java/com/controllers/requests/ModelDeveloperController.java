package com.controllers.requests;

import com.entity.ModelDeveloperEntity;
import com.google.gson.Gson;
import com.tools.Constant;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

public class ModelDeveloperController extends MainController {

    public static HttpResponse createModelDeveloper(ModelDeveloperEntity modelDeveloperEntity) {
        String json = new Gson().toJson(modelDeveloperEntity);
        String url = getUrl() + "/specialist/" + modelDeveloperEntity.getEmployeeStatus().getId();
        HttpPost request = new HttpPost(url);
        HttpResponse response = crudEntity(json, request, null, null, null);
        return response;
    }

    public static HttpResponse changeModelDeveloper(ModelDeveloperEntity modelDeveloperEntity) {
        String json = new Gson().toJson(modelDeveloperEntity);
        String url = getUrl() + "/specialist/" + modelDeveloperEntity.getId() + "/" + modelDeveloperEntity.getEmployeeStatus().getId();
        HttpPut request = new HttpPut(url);
        HttpResponse response = crudEntity(json, null, null, request, null);
        return response;
    }

    public static HttpResponse getModelDeveloperPage(int pageIndex) {
        String url = getUrl() + "/specialist/all/" + pageIndex + "/" + Constant.getObjectOnPage();
        HttpGet request = new HttpGet(url);
        HttpResponse response = crudEntity(null, null, request, null, null);
        return response;
    }
}
