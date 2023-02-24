package com.oofile.entitiy;

import com.oofile.enums.FileTypeEnum;

public class TextFileEntity extends BaseEntity {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
