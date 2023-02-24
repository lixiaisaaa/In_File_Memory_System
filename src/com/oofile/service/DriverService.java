package com.oofile.service;

import com.oofile.entitiy.BaseEntity;
import com.oofile.memory.FileMemory;
import com.oofile.utils.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverService {

    public Boolean createDriver(String driverName){

        driverName = driverName.toUpperCase();

        if(isDriverExistByDriverName(driverName)){
            System.out.println("Driver name "+driverName+" is exist.");
            return false;
        }

        if(CommonUtil.checkDriverName(driverName)==false){
            System.out.println("Driver name must between A-Z.");
            return false;
        }

        Map<String,List<String>> folderMap = new HashMap<>();
        folderMap.put(driverName+":\\",new ArrayList<>());
        FileMemory.folderMap.put(driverName,folderMap);

        Map<String,List<BaseEntity>> fileMap = new HashMap<>();
        fileMap.put(driverName+":\\",new ArrayList<>());
        FileMemory.fileMap.put(driverName,fileMap);

        System.out.println("Driver "+driverName+" create successfully.");

        return true;
    }

    public Boolean isDriverExist(String filePath){
        if(filePath==null || "".equals(filePath)){
            return false;
        }
        return isDriverExistByDriverName(CommonUtil.getDriverNameByPath(filePath));
    }

    public Boolean isDriverExistByDriverName(String driverName){
        driverName = driverName.toUpperCase();
        Map<String,List<String>> folderMap = FileMemory.folderMap.get(driverName);
        if(folderMap==null){
            return false;
        }
        return true;
    }
}
