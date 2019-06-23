package com.controllers.requests;


import com.tools.Constant;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicStatusLine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

public abstract class MainController {
    private final static String LOCALHOST_URL = "http://localhost:8888";
//    private final static String LOCALHOST_URL = "http://111810cd.ngrok.io";
    private final static String DOCTOR_URL = "/doctor-system/admin";
    private final static String URL = LOCALHOST_URL + DOCTOR_URL;
    private final static String SETTING_FILE_NAME = "config.ini";

    public static String getUrl() {
        try {
            FileReader fileReader = new FileReader(SETTING_FILE_NAME);
            Scanner scanner = new Scanner(fileReader);
            return scanner.nextLine() + DOCTOR_URL;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return URL;
        }
    }

    public static HttpResponse crudEntity(HttpEntity httpEntity, HttpPost post, HttpGet get, HttpPut put, HttpDelete delete) {
        String basicAuthPayload = "Basic " + Base64.getEncoder().encodeToString((Constant.getAuth()[0] + ":" + Constant.getAuth()[1]).getBytes());
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpRequestBase request = null;
        if (post != null) {

            post.setHeader("Content-Type", "application/json");
            if(httpEntity!=null) {
                post.setEntity(httpEntity);
            }
            request = post;

        } else if (put != null) {

            put.setHeader("Content-Type", "application/json");
            if(httpEntity!=null) {
                put.setEntity(httpEntity);
            }
            request = put;

        } else if (get != null) {
            request = get;
        } else if (delete != null) {
            delete.setHeader("Content-Type", "application/json");
            request = delete;
        }
        request.addHeader("Authorization", basicAuthPayload);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (HttpHostConnectException e) {
            HttpResponseFactory httpResponseFactory = new DefaultHttpResponseFactory();
            response = httpResponseFactory.newHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_GATEWAY_TIMEOUT, null), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
