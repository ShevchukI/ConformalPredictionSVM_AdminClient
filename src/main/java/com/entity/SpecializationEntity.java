package com.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SpecializationEntity {
    private int id;
    private String name;

    public SpecializationEntity() {
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

    public static ArrayList<SpecializationEntity> getListFromResponse(HttpResponse response)  {
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
