package com.oofile.enums;

public enum FileTypeEnum {
    TEXT("TEXT", "TEXT"),
    ZIP("ZIP", "ZIP"),
            ;

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    FileTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
