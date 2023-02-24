package com.oofile;

import com.oofile.memory.FileMemory;
import com.oofile.service.DriverService;
import com.oofile.service.FolderService;
import com.oofile.service.TextFileService;
import com.oofile.service.ZipFileService;

import java.util.Scanner;

public class StartApplication {

    private static Scanner in;

    public static void main(String[] args) {

        FileMemory.getInstance();
        initInput();

        while(true){
            showMenu();
            String menuId = selectMenu();
            if(checkMenuId(menuId)==false){
                System.out.println("\nWrong option, please select again.\n");
                continue;
            }
            execute(menuId);
        }

    }

    private static void initInput(){
        if(in==null){
            in = new Scanner(System.in);
        }
    }

    private static void showMenu(){
        System.out.println("Welcome to OO File System");
        System.out.println("===========================");
        System.out.println("1: Create Driver");
        System.out.println("2: Create Folder");
        System.out.println("3: List Folder");
        System.out.println("4: Create Text File");
        System.out.println("5: Edit Text File");
        System.out.println("6: Read Text File");
        System.out.println("7: Move Folder / File");
        System.out.println("8: Create Zip File");
        System.out.println("9: Delete Folder / File");
        System.out.println("10: Exit");
        System.out.println("===========================");
    }

    private static String selectMenu(){
        System.out.print("Please Select: ");
        return in.next();
    }

    private static Boolean checkMenuId(String menuId){
        try{
            Integer m = Integer.parseInt(menuId);
            if(m<1 || m>10){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private static void execute(String menuId){
        DriverService driverService = new DriverService();
        FolderService folderService = new FolderService();
        TextFileService textFileService = new TextFileService();
        ZipFileService zipFileService = new ZipFileService();

        switch (menuId){
            case "1":
                System.out.print("Please input Driver name: ");
                String driverName = in.next();
                driverService.createDriver(driverName);
                break;
            case "2":
                System.out.print("Please input folder path: ");
                String folderPath = in.next();
                folderService.createFolder(folderPath);
                break;
            case "3":
                System.out.print("Please input folder path: ");
                folderPath = in.next();
                folderService.listFolder(folderPath);
                break;
            case "4":
                System.out.print("Please input txt file path: ");
                String filePath = in.next();
                textFileService.createTextFile(filePath);
                break;
            case "5":
                System.out.print("Please input txt file path: ");
                filePath = in.next();
                textFileService.editTextFile(filePath);
                break;
            case "6":
                System.out.print("Please input txt file path: ");
                filePath = in.next();
                textFileService.readTextFile(filePath);
                break;
            case "7":
                System.out.print("Please input original path: ");
                String originalPath = in.next();
                System.out.print("Please input destination path: ");
                String destinationPath = in.next();
                folderService.move(originalPath,destinationPath);
                break;
            case "8":
                System.out.print("Please input a path you need to zip: ");
                String zipPath = in.next();
                System.out.print("Please input zip save path: ");
                String savePath = in.next();
                zipFileService.zip(zipPath,savePath);
                break;
            case "9":
                System.out.print("Please input folder / file path: ");
                String path = in.next();
                folderService.delete(path);
                break;
            case "10":
                System.exit(-1);
                break;
        }
        try{
            System.out.print("Enter any key to continue..");
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
