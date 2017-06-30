package com.ehome.cloud.sys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

@Table(name = "t_uploadfile")
public class UploadFile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -575427494685559431L;

    @Column(name = "originalname")
    private String originalName;

    @Column(name = "currentname")
    private String currentName;

    @Column(name = "filepath")
    private String filePath;
    
    @Column(name = "original")
    private int original;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "content")
    private String content;
    
    //标志位 0 正常  -1已删除
    @Column(name = "flag")
    private int flag;

    public UploadFile(){
    }
    
    public UploadFile(String originalName, String currentName, String filePath, int original) {
        this.originalName = originalName;
        this.currentName = currentName;
        this.filePath = filePath;
        this.original = original;
    }
    
    public UploadFile(String originalName, String currentName, String filePath, int original,String title, String content) {
        this.originalName = originalName;
        this.currentName = currentName;
        this.filePath = filePath;
        this.original = original;
        this.title = title;
        this.content = content;
    }
    
    public UploadFile(String originalName, String currentName, String filePath, int original,String title, String content, int flag) {
        this.originalName = originalName;
        this.currentName = currentName;
        this.filePath = filePath;
        this.original = original;
        this.title = title;
        this.content = content;
        this.flag = flag;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }   
}
