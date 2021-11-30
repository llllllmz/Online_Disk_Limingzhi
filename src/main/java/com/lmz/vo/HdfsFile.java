package com.lmz.vo;

import org.apache.hadoop.fs.Path;

public class HdfsFile {
    private String name;
    private Boolean isFile;
    private long len;
    private Path path;
    private long modificationTime;


    public HdfsFile() {
    }

    public HdfsFile(String name, Boolean isFile, long len, Path path, long modificationTime) {
        this.name = name;
        this.isFile = isFile;
        this.len = len;
        this.path = path;
        this.modificationTime = modificationTime;
    }

    public long getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(long modificationTime) {
        this.modificationTime = modificationTime;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsFile() {
        return isFile;
    }

    public void setIsFile(Boolean file) {
        isFile = file;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }
}
