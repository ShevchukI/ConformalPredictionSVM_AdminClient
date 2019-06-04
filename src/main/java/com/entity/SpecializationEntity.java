package com.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tools.Constant;
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

public class SpecializationEntity {
    private int id;
    private String name;

    public SpecializationEntity() {
    }

    public void addNew(String name) {
        this.name = name;
        HttpResponse response = createSpecialization(this);
        if(response.getStatusLine().getStatusCode()==201){
            try {
                this.id = Integer.parseInt(Constant.responseToString(response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private HttpResponse createSpecialization(SpecializationEntity specializationEntity) {
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

    public int change(String name) {
        boolean result = Constant.questionOkCancel("Do you really want to change specialization "
                + name + "?");
        if (result) {
            this.name = name;
            HttpResponse response = changeSpecialization(this);
            return response.getStatusLine().getStatusCode();
        } else {
            return 0;
        }
    }

    private HttpResponse changeSpecialization(SpecializationEntity specializationEntity) {
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

    public int deleteSpecialization() {
        boolean result = Constant.questionOkCancel("Do you really want to delete Specialization "
                + this.getName() + " ?");
        if (result) {
            HttpResponse response = deleteSpecialization(this);
            return response.getStatusLine().getStatusCode();
        } else {
            return 0;
        }
    }

    private HttpResponse deleteSpecialization(SpecializationEntity specializationEntity) {
        String url = getUrl() + "/specialization/" + specializationEntity.getId();
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = crudEntity(null, null, null, null, request);
        return response;
    }

    public static HttpResponse getAllSpecialization(){
        String url = getUrl() + "/specialization/all";
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

    public static ArrayList<SpecializationEntity> getListFromResponse(HttpResponse response) {
        ArrayList<SpecializationEntity> specializationEntities;
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
            Type founderListType = new TypeToken<ArrayList<SpecializationEntity>>() {
            }.getType();
            specializationEntities = gson.fromJson(json, founderListType);
            return specializationEntities;
        } else {
            return null;
        }
    }



}
