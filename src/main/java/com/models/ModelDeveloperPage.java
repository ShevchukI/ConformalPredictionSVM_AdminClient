package com.models;

import com.entity.ModelDeveloperEntity;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ModelDeveloperPage {
    private int numberOfPages;
    private List<ModelDeveloperEntity> specialistEntities;

    public static ModelDeveloperPage fromJson(HttpResponse response) {
        BufferedReader reader = null;
        String json = null;
        try {
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            json = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(json, ModelDeveloperPage.class);
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public List<ModelDeveloperEntity> getSpecialistEntities() {
        return specialistEntities;
    }

}
