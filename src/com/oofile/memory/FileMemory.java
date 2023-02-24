package com.oofile.memory;

import com.oofile.entitiy.BaseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileMemory {

    public static Map<String, Map<String, List<BaseEntity>>> fileMap;

    public static Map<String,Map<String,List<String>>> folderMap;

    public static void getInstance(){
        if(fileMap==null){
            fileMap = new HashMap<>();
        }
        if(folderMap==null){
            folderMap = new HashMap<>();
        }
    }
}
