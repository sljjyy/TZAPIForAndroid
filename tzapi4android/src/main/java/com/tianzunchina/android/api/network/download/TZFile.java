package com.tianzunchina.android.api.network.download;

import org.json.JSONObject;

import java.io.Serializable;

public abstract class TZFile implements Serializable{

    private String fileName;//文件名
    private String filenameExtension;//文件扩展名
    private String downloadURL;//下载的路径

    public TZFile() {
    }

    /**
     * 根据接口获取的JSONObject解析成TZFile对象
     * @param jsonObject 接口获取的值
     */
    public abstract void parse(JSONObject jsonObject);

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilenameExtension() {
        return filenameExtension;
    }

    public void setFilenameExtension(String filenameExtension) {
        this.filenameExtension = filenameExtension;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
}
