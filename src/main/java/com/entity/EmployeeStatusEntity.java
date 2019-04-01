package com.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EmployeeStatusEntity {
    private int id;
    private String name;
    private boolean workEnable;

    public EmployeeStatusEntity() {
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

    public boolean isWorkEnable() {
        return workEnable;
    }

    public void setWorkEnable(boolean workEnable) {
        this.workEnable = workEnable;
    }

    public String getVisibleWorkEnable() {
        if (this.isWorkEnable()) {
            return "Allow";
        } else {
            return "Not allow";
        }
    }

    public static ArrayList<EmployeeStatusEntity> getListFromResponse(HttpResponse response) throws IOException {
        ArrayList<EmployeeStatusEntity> employeeStatusEntities;
        if (response.getStatusLine().getStatusCode() == 200) {
            StringBuilder stringBuilder = new StringBuilder();
            DataInputStream dataInputStream = new DataInputStream(response.getEntity().getContent());
            String line;
            while ((line = dataInputStream.readLine()) != null) {
                stringBuilder.append(line);
            }
            dataInputStream.close();
            String json = stringBuilder.toString();
            Gson gson = new Gson();
            Type founderListType = new TypeToken<ArrayList<EmployeeStatusEntity>>() {
            }.getType();
            employeeStatusEntities = gson.fromJson(json, founderListType);
            return employeeStatusEntities;
        } else {
            return null;
        }
    }
}
