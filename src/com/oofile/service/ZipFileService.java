package com.oofile.service;

import com.oofile.entitiy.BaseEntity;
import com.oofile.entitiy.ZipFileEntity;
import com.oofile.memory.FileMemory;
import com.oofile.utils.CommonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZipFileService {

    public void zip(String zipPath, String savePath){
        if(zipPath==null || "".equals(zipPath)){
            System.out.println("Please input zip path");
            return;
        }
        if(savePath==null || "".equals(savePath)){
            System.out.println("Please input zip save path");
            return;
        }
        if(savePath.indexOf(".zip") <= 0){
            System.out.println("Zip file save path must end with .zip");
            return;
        }

        String savePathFolderStr = savePath.substring(0,savePath.lastIndexOf("\\")+1);
        String savePathFileStr = savePath.substring(savePath.lastIndexOf("\\")+1);

        Map<String,List<BaseEntity>> saveFileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(savePathFolderStr));
        List<BaseEntity> saveFileList = saveFileMap.get(savePathFolderStr);

        for (int i = 0 ; i < saveFileList.size(); i++) {
            BaseEntity entity = saveFileList.get(i);
            if(savePathFileStr.equals(entity.getFileName())){
                System.out.println("zip save path already exist, please check.");
                return;
            }
        }

        Map<String,List<BaseEntity>> zipPathMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(zipPath));
        List<BaseEntity> zipPathList = zipPathMap.get(zipPath);
        if(zipPathList==null){
            System.out.println("the zip path does not exist, please check.");
            return;
        }

        //remove path in folder map and file map
        FileMemory.folderMap.get(CommonUtil.getDriverNameByPath(zipPath)).remove(zipPath);
        FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(zipPath)).remove(zipPath);


        //save path into zip
        ZipFileEntity zipFileEntity = new ZipFileEntity();
        zipFileEntity.setFileName(savePathFileStr);
        zipFileEntity.setFilePath(savePathFolderStr);
        zipFileEntity.setFileSize(Double.valueOf(zipPathList.size()/2));
        zipPathMap = new HashMap<>();
        zipPathMap.put("/",zipPathList);
        zipFileEntity.setFileMap(zipPathMap);

        //save zip file
        saveFileList.add(zipFileEntity);
        FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(savePathFolderStr)).put(savePathFolderStr,saveFileList);

        System.out.println("Folder zip successfully.");
    }

}
