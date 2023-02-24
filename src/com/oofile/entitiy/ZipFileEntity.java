package com.oofile.entitiy;

import java.util.List;
import java.util.Map;

public class ZipFileEntity extends BaseEntity {

    private Map<String, List<BaseEntity>> fileMap;

    public Map<String, List<BaseEntity>> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, List<BaseEntity>> fileMap) {
        this.fileMap = fileMap;
    }
}
