package com.controllers.requests;


public abstract class MainController {
    private final static String LOCALHOST_URL = "http://localhost:8888";
    private final static String DOCTOR_URL = "/doctor-system/admin";
    private final static String URL = LOCALHOST_URL + DOCTOR_URL;

    protected static String getUrl(){
        return URL;
    }

}
