package com.oofile.service;

import com.oofile.entitiy.BaseEntity;
import com.oofile.memory.FileMemory;
import com.oofile.utils.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FolderService {

    public Boolean createFolder(String folderPath){

        if(folderPath==null || "".equals(folderPath)){
            System.out.println("Folder path is not a valid directory.");
            return false;
        }

        if(CommonUtil.checkFolderPath(folderPath)==false){
            System.out.println("Folder path is not a valid directory.");
            return false;
        }

        if(!"\\".equals(folderPath.substring(folderPath.length()-1))){
            folderPath = folderPath + "\\";
        }

        DriverService driverService = new DriverService();

        if(driverService.isDriverExist(folderPath)==false){
            System.out.println("Driver name not exist, please create driver first.");
            return false;
        }

        if(isFolderExist(folderPath)){
            System.out.println(folderPath + " already exist.");
            return false;
        }

        String driverName = CommonUtil.getDriverNameByPath(folderPath);

        Map<String, List<String>> folderMap = FileMemory.folderMap.get(driverName);
        if(folderMap==null){
            folderMap = new HashMap<>();
        }
        folderMap.put(folderPath,new ArrayList<>());
        FileMemory.folderMap.put(driverName,folderMap);

        Map<String,List<BaseEntity>> fileMap = FileMemory.fileMap.get(driverName);
        if(fileMap==null){
            fileMap = new HashMap<>();
        }

        fileMap.put(folderPath,new ArrayList<>());
        FileMemory.fileMap.put(driverName,fileMap);

        System.out.println(folderPath+" create successfully.");

        return true;
    }

    public Boolean isFolderExist(String folderPath){
        if(folderPath==null || "".equals(folderPath)){
            return false;
        }
        Map<String,List<String>> folderMap = FileMemory.folderMap.get(CommonUtil.getDriverNameByPath(folderPath));
        if(folderMap==null){
            return false;
        }
        if(folderMap.get(folderPath)==null){
            return false;
        }
        return true;
    }

    public void move(String originalPath, String destinationPath){
        if(originalPath==null || "".equals(originalPath)){
            System.out.println("Please input original path");
            return;
        }
        if(destinationPath==null || "".equals(destinationPath)){
            System.out.println("Please input destination path");
            return;
        }

        //is file
        if(originalPath.indexOf(".txt") > 0 || originalPath.indexOf(".zip") > 0){

            String originalFolderStr = originalPath.substring(0,originalPath.lastIndexOf("\\")+1);
            String originalFileStr = originalPath.substring(originalPath.lastIndexOf("\\")+1);
            String destinationFolderStr = destinationPath.substring(0,destinationPath.lastIndexOf("\\")+1);
            String destinationFileStr = destinationPath.substring(destinationPath.lastIndexOf("\\")+1);

            Map<String,List<BaseEntity>> fileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(originalFolderStr));
            List<BaseEntity> fileList = fileMap.get(originalFolderStr);

            BaseEntity curEntity = null;
            Integer originalFileId = null;
            for (int i = 0 ; i < fileList.size(); i++) {
                BaseEntity entity = fileList.get(i);
                if(originalFileStr.equals(entity.getFileName())){
                    curEntity = entity;
                    originalFileId = i;
                }
            }

            if(originalFileId==null){
                System.out.println("Original file does not exist, please check.");
                return;
            }

            Map<String,List<BaseEntity>> destFileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(destinationFolderStr));
            List<BaseEntity> destFileList = fileMap.get(destinationFolderStr);

            for (int i = 0 ; i < destFileList.size(); i++) {
                BaseEntity entity = destFileList.get(i);
                if(destinationFileStr.equals(entity.getFileName())){
                    System.out.println("Destination file already exist, please check.");
                    return;
                }
            }

            //remove original file
            fileList.remove(curEntity);
            FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(originalFolderStr)).put(originalFolderStr, fileList);

            curEntity.setFilePath(destinationFolderStr);
            destFileList.add(curEntity);
            FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(destinationFolderStr)).put(destinationFolderStr, destFileList);

            System.out.println("File move successfully.");

        }else{

            if(!"\\".equals(originalPath.substring(originalPath.length()-1))){
                originalPath = originalPath + "\\";
            }
            if(!"\\".equals(destinationPath.substring(destinationPath.length()-1))){
                destinationPath = destinationPath + "\\";
            }

            Map<String,List<String>> originalFolderMap = FileMemory.folderMap.get(CommonUtil.getDriverNameByPath(originalPath));
            if(originalFolderMap==null || originalFolderMap.get(originalPath)==null){
                System.out.println("Original Path does not exist, please check.");
                return;
            }

            Map<String,List<String>> destFolderMap = FileMemory.folderMap.get(CommonUtil.getDriverNameByPath(destinationPath));
            if(destFolderMap==null || destFolderMap.get(destinationPath)!=null){
                System.out.println("Destination Path already exist, please check.");
                return;
            }

            //remove original folder
            List<String> folderList = originalFolderMap.get(originalPath);
            FileMemory.folderMap.remove(originalPath);

            //put folder list into new key
            FileMemory.folderMap.get(CommonUtil.getDriverNameByPath(destinationPath)).put(destinationPath,folderList);

            //remove original files
            Map<String,List<BaseEntity>> fileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(originalPath));
            List<BaseEntity> fileList = fileMap.get(originalPath);
            FileMemory.fileMap.remove(originalPath);

            for (BaseEntity entity : fileList) {
                entity.setFilePath(destinationPath);
            }

            //put all files into new key
            FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(destinationPath)).put(destinationPath,fileList);

            System.out.println("Folder move successfully.");
        }
    }

    public void delete(String path){
        if(path==null || "".equals(path)){
            System.out.println("Please input path");
            return;
        }

        //is file
        if(path.indexOf(".txt") > 0 || path.indexOf(".zip") > 0){

            String folderStr = path.substring(0,path.lastIndexOf("\\")+1);
            String fileStr = path.substring(path.lastIndexOf("\\")+1);

            Map<String,List<BaseEntity>> fileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(folderStr));
            List<BaseEntity> fileList = fileMap.get(folderStr);

            BaseEntity curEntity = null;
            Integer originalFileId = null;
            for (int i = 0 ; i < fileList.size(); i++) {
                BaseEntity entity = fileList.get(i);
                if(fileStr.equals(entity.getFileName())){
                    curEntity = entity;
                    originalFileId = i;
                }
            }

            if(originalFileId==null){
                System.out.println("File does not exist, please check.");
                return;
            }

            //remove original file
            fileList.remove(curEntity);
            FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(folderStr)).put(folderStr, fileList);

            System.out.println("File delete successfully.");

        }else{

            if(!"\\".equals(path.substring(path.length()-1))){
                path = path + "\\";
            }

            Map<String,List<String>> originalFolderMap = FileMemory.folderMap.get(CommonUtil.getDriverNameByPath(path));
            if(originalFolderMap==null || originalFolderMap.get(path)==null){
                System.out.println("path does not exist, please check.");
                return;
            }

            //remove original folder
            FileMemory.folderMap.get(CommonUtil.getDriverNameByPath(path)).remove(path);

            //remove original files
            FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(path)).remove(path);

            System.out.println("Folder delete successfully.");
        }
    }

    public void listFolder(String folderPath){

        if(CommonUtil.checkFolderPath(folderPath)==false){
            System.out.println("Folder path is not a valid directory.");
            return;
        }

        System.out.println("\n**********");

        if(folderPath==null || "".equals(folderPath)){
            System.out.println("**********\n");
            return;
        }
        if(!"\\".equals(folderPath.substring(folderPath.length()-1))){
            folderPath = folderPath + "\\";
        }

        System.out.println(folderPath);
        System.out.println("**********");

        Map<String,List<String>> folderMap = FileMemory.folderMap.get(CommonUtil.getDriverNameByPath(folderPath));
        if(folderMap==null){
            System.out.println("Driver name " + CommonUtil.getDriverNameByPath(folderPath) +" not exist, please create driver first.");
            System.out.println("**********\n");
            return;
        }
        if(folderMap.get(folderPath)==null){
            System.out.println("Folder path " + folderPath +" not exist, please create first.");
            System.out.println("**********\n");
            return;
        }

        //ergodic folder map first
        for(String path : folderMap.keySet()){
            if(!path.equals(folderPath) && path.indexOf(folderPath) >= 0){
                System.out.println(path);
            }
        }


        Map<String,List<BaseEntity>> fileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(folderPath));
        if(fileMap==null){
            System.out.println("**********\n");
            return;
        }
        if(fileMap.get(folderPath)==null || fileMap.get(folderPath).size()==0){
            System.out.println("**********\n");
            return;
        }

        //ergodic file map which in folder key
        for(BaseEntity entity : fileMap.get(folderPath)){
            System.out.println(entity.getFilePath() + entity.getFileName());
        }

        System.out.println("**********\n");

        return;
    }

}
