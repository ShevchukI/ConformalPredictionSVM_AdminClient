package com.tools;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazleCastMap {
    private static final String INSTANCE_NAME = "mainAdminInstance";
    private static final String USER_MAP_NAME = "admin";
    private static final String KEY_MAP_NAME = "key";
    private static final String MISCELLANEOUS_MAP_NAME = "misc";

    public static void createInstanceAndMap() {
        Config config = new Config();
        config.setInstanceName(INSTANCE_NAME);
        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.setPort(2019);
        config.addMapConfig(createMapWithName(USER_MAP_NAME));
        config.addMapConfig(createMapWithName(KEY_MAP_NAME));
        config.addMapConfig(createMapWithName(MISCELLANEOUS_MAP_NAME));
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName(USER_MAP_NAME);
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    public static HazelcastInstance getInstance() {
        return Hazelcast.getHazelcastInstanceByName(INSTANCE_NAME);
    }

    public static IMap getMapByName(String mapName) {
        return Hazelcast.getHazelcastInstanceByName(INSTANCE_NAME).getMap(mapName);
    }

    private static MapConfig createMapWithName(String mapName) {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName(mapName);
        return mapConfig;
    }

    public static void fillUserMap(String[] authorization){
        String login = authorization[0];
        String password = authorization[1];
        String key = Encryptor.genRandString();
        String vector = Encryptor.genRandString();
        getMapByName(USER_MAP_NAME).put(Constant.getLOGIN(), Encryptor.encrypt(key, vector, login));
        getMapByName(USER_MAP_NAME).put(Constant.getPASSWORD(), Encryptor.encrypt(key, vector, password));
        getMapByName(KEY_MAP_NAME).put(Constant.getKEY(), key);
        getMapByName(KEY_MAP_NAME).put(Constant.getVECTOR(), vector);
        getMapByName(MISCELLANEOUS_MAP_NAME).put(Constant.getPageIndexDoctor(), 1);
        getMapByName(MISCELLANEOUS_MAP_NAME).put(Constant.getPageIndexModelDeveloper(), 1);
    }

    public static String getUserMapName() {
        return USER_MAP_NAME;
    }

    public static String getKeyMapName() {
        return KEY_MAP_NAME;
    }

    public static String getMiscellaneousMapName() {
        return MISCELLANEOUS_MAP_NAME;
    }
}
