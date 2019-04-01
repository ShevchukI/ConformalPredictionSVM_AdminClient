package com.controllers.windows.tab;

import com.controllers.windows.menu.MenuController;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class DoctorTabController extends MenuController{
    @Autowired
    HttpResponse response;

    private MenuController menuController;


}
