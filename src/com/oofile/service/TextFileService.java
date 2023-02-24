package com.oofile.service;

import com.oofile.entitiy.BaseEntity;
import com.oofile.entitiy.TextFileEntity;
import com.oofile.enums.FileTypeEnum;
import com.oofile.memory.FileMemory;
import com.oofile.utils.CommonUtil;

import java.util.*;

public class TextFileService {

    public Boolean createTextFile(String filePath){

        if(filePath==null || "".equals(filePath)){
            System.out.println("File path is not a valid directory.");
            return false;
        }

        if("\\".equals(filePath.substring(filePath.length()-1))){
            System.out.println("File path must be a file, not a folder.");
            return false;
        }

        String folderPath = filePath.substring(0,filePath.lastIndexOf("\\")+1);
        String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);

        //check file extension name
        if(fileName.toLowerCase().indexOf(".txt") <= 0){
            System.out.println("File extension name must be .txt");
            return false;
        }

        //check folder path normative
        if(CommonUtil.checkFolderPath(folderPath)==false){
            System.out.println("Folder path is not a valid directory.");
            return false;
        }

        DriverService driverService = new DriverService();
        FolderService folderService = new FolderService();

        //check driver is exist
        if(driverService.isDriverExist(folderPath)==false){
            System.out.println("Driver name not exist, please create driver first.");
            return false;
        }

        //check folder is exist
        if(folderService.isFolderExist(folderPath)==false){
            System.out.println(folderPath + " not exist, please create folder first.");
            return false;
        }

        Map<String,List<BaseEntity>> fileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(folderPath));
        List<BaseEntity> fileList = fileMap.get(folderPath);
        if(fileList==null){
            fileList = new ArrayList<>();
        }

        for (BaseEntity entity : fileList) {
            if(fileName.equals(entity.getFileName())){
                System.out.println(fileName + " is exist, please rename txt file.");
                return false;
            }
        }

        //content can be null
        System.out.print("Please input content for txt file: ");
        Scanner in = new Scanner(System.in);
        String content = in.next();

        TextFileEntity entity = new TextFileEntity();
        entity.setFileName(fileName);
        entity.setFilePath(folderPath);
        entity.setFileType(FileTypeEnum.TEXT);
        entity.setFileSize(content==null?Double.parseDouble("0"):content.length());
        entity.setContent(content);


        fileList.add(entity);
        fileMap.put(folderPath,fileList);

        System.out.println(filePath + " create successfully.");

        return true;
    }


    public Boolean editTextFile(String filePath){

        if(filePath==null || "".equals(filePath)){
            System.out.println("File path is not a valid directory.");
            return false;
        }

        if("\\".equals(filePath.substring(filePath.length()-1))){
            System.out.println("File path must be a file, not a folder.");
            return false;
        }

        String folderPath = filePath.substring(0,filePath.lastIndexOf("\\")+1);
        String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);

        //check file extension name
        if(fileName.toLowerCase().indexOf(".txt") <= 0){
            System.out.println("File extension name must be .txt");
            return false;
        }

        //check folder path normative
        if(CommonUtil.checkFolderPath(folderPath)==false){
            System.out.println("Folder path is not a valid directory.");
            return false;
        }

        DriverService driverService = new DriverService();
        FolderService folderService = new FolderService();

        //check driver is exist
        if(driverService.isDriverExist(folderPath)==false){
            System.out.println("Driver name not exist, please create driver first.");
            return false;
        }

        //check folder is exist
        if(folderService.isFolderExist(folderPath)==false){
            System.out.println(folderPath + " not exist, please create folder first.");
            return false;
        }

        Map<String,List<BaseEntity>> fileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(folderPath));
        List<BaseEntity> fileList = fileMap.get(folderPath);
        if(fileList==null){
            fileList = new ArrayList<>();
        }

        Integer fileIndex = -1;
        TextFileEntity textFileEntity = null;

        for (int i = 0; i < fileList.size(); i++) {
            BaseEntity entity = fileList.get(i);
            if(fileName.equals(entity.getFileName()) && entity instanceof TextFileEntity){
                fileIndex = i;
                textFileEntity = (TextFileEntity)entity;
                break;
            }
        }

        if(textFileEntity==null){
            System.out.println(filePath + " not exist, please check.");
            return false;
        }

        System.out.print("Please input content for txt file: ");
        Scanner in = new Scanner(System.in);
        String content = in.next();

        textFileEntity.setFileSize(content==null?Double.parseDouble("0"):content.length());
        textFileEntity.setContent(content);

        fileList.set(fileIndex,textFileEntity);
        fileMap.put(folderPath,fileList);

        System.out.println(filePath + " edit successfully.");

        return true;
    }

    public void readTextFile(String filePath){

        if(filePath==null || "".equals(filePath)){
            System.out.println("File path is not a valid directory.");
            return;
        }

        if("\\".equals(filePath.substring(filePath.length()-1))){
            System.out.println("File path must be a file, not a folder.");
            return;
        }

        String folderPath = filePath.substring(0,filePath.lastIndexOf("\\")+1);
        String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);

        //check file extension name
        if(fileName.toLowerCase().indexOf(".txt") <= 0){
            System.out.println("File extension name must be .txt");
            return;
        }

        //check folder path normative
        if(CommonUtil.checkFolderPath(folderPath)==false){
            System.out.println("Folder path is not a valid directory.");
            return;
        }

        Map<String,List<BaseEntity>> fileMap = FileMemory.fileMap.get(CommonUtil.getDriverNameByPath(folderPath));
        List<BaseEntity> fileList = fileMap.get(folderPath);
        if(fileList==null){
            fileList = new ArrayList<>();
        }

        Integer fileIndex = -1;
        TextFileEntity textFileEntity = null;

        for (int i = 0; i < fileList.size(); i++) {
            BaseEntity entity = fileList.get(i);
            if(fileName.equals(entity.getFileName()) && entity instanceof TextFileEntity){
                fileIndex = i;
                textFileEntity = (TextFileEntity)entity;
                break;
            }
        }

        if(textFileEntity==null){
            System.out.println(filePath + " not exist, please check.");
            return;
        }


        System.out.println("\n**********");
        System.out.println(fileName);
        System.out.println("**********");
        System.out.println(textFileEntity.getContent());
        System.out.println("**********");

    }

}
