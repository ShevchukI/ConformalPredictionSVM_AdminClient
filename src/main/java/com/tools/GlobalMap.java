package com.tools;

import java.util.HashMap;
import java.util.Map;

public class GlobalMap {
    private static GlobalMap ourInstance = new GlobalMap();

    private static Map<String, String> userMap = new HashMap<>();
    private static Map<String, String> keyMap = new HashMap<>();
    private static Map<String, String> miscMap = new HashMap<>();

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
        getUserMap().put(Constant.getLOGIN(), Encryptor.encrypt(key, vector, login));
        getUserMap().put(Constant.getPASSWORD(), Encryptor.encrypt(key, vector, password));
        getKeyMap().put(Constant.getKEY(), key);
        getKeyMap().put(Constant.getVECTOR(), vector);
        getMiscMap().put(Constant.getPageIndexDoctor(), "1");
        getMiscMap().put(Constant.getPageIndexModelDeveloper(), "1");
    }

    private GlobalMap() {
    }
}
