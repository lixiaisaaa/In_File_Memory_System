package com.oofile.utils;

public class CommonUtil {

    public static Boolean checkDriverName(String driverName){
        if(driverName==null || "".equals(driverName)){
            return false;
        }
        if(driverName.length()>1){
            return false;
        }
        char dr = driverName.toLowerCase().charAt(0);
        if(dr>='a' && dr<='z'){
            return true;
        }
        return false;
    }

    public static String getDriverNameByPath(String filePath){
        if(filePath==null || filePath.indexOf(":") == -1){
            return null;
        }
        return filePath.substring(0,filePath.indexOf(":"));
    }



    public static Boolean checkFolderPath(String folderPath){
        if(folderPath==null || "".equals(folderPath) || folderPath.length() < 3){
            return false;
        }
        if(!":".equals(folderPath.substring(1,2)) || !"\\".equals(folderPath.substring(2,3))){
            return false;
        }
        return true;
    }

}
