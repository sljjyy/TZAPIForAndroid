package com.tianzunchina.sample.model;

import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem;
import com.tianzunchina.sample.widget.LinearLayoutItem;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/19.
 * 本地数据——社区
 */


public class Region extends ArrayAdapterItem implements Serializable, LinearLayoutItem {
    public Region(int id, int parentID, String val, String des) {
        super(id, parentID, val, des);
    }

    public int getRegionID() {
        return getIntID();
    }

    public void setRegionID(int regionID) {
        setID(regionID);
    }

    public String getRegionName() {
        return getVal();
    }

    public String getDescription() {
        return getDes();
    }

    @Override
    public String getTag() {
        return getRegionName().replace("支部", "");
    }

    @Override
    public int getTagID() {
        return getRegionID();
    }
}