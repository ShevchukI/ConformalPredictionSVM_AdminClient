package com.tools;

import java.util.HashMap;
import java.util.Map;

public class GlobalMap {
    private static GlobalMap ourInstance = new GlobalMap();

    private static Map<String, String> userMap;
    private static Map<String, String> keyMap;
    private static Map<String, String> miscMap;

    public static GlobalMap getInstance() {
        return ourInstance;
    }

    public static Map<String, String> getUserMap(){
        return userMap;
    }

    public static Map<String, String> getKeyMap(){
        return keyMap;
    }

    public static Map<String, String> getMiscMap(){
        return miscMap;
    }

    public static void fillMap(String[] authorization){
        String login = authorization[0];
        String password = authorization[1];
        String key = Encryptor.genRandString();
        String vector = Encryptor.genRandString();
        getKeyMap().put(Constant.getKEY(), key);
        getKeyMap().put(Constant.getVECTOR(), vector);
        getUserMap().put(Constant.getLOGIN(), Encryptor.encrypt(key, vector, login));
        getUserMap().put(Constant.getPASSWORD(), Encryptor.encrypt(key, vector, password));
        getMiscMap().put(Constant.getPageIndexDoctor(), "1");
        getMiscMap().put(Constant.getPageIndexModelDeveloper(), "1");
    }

    private GlobalMap() {
        userMap = new HashMap<>();
        keyMap = new HashMap<>();
        miscMap = new HashMap<>();
    }
}
