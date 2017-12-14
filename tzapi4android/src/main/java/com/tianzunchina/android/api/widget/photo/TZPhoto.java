package com.tianzunchina.android.api.widget.photo;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class TZPhoto {

    private List<String> paths;
    private List<String> dates;
    private String date;//获取图片的拍摄时间
    private File fileImage;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public File getFileImage() {
        return fileImage;
    }

    public void setFileImage(File fileImage) {
        this.fileImage = fileImage;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}
