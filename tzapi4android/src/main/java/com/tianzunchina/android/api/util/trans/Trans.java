package com.tianzunchina.android.api.util.trans;

/**
 * 坐标系转换
 */
public class Trans {
    private CCoordTrans cct;

    public Trans(int sysID) {
        this.cct = null;
        this.cct = new CCoordTrans(sysID);
    }

    public Trans(int sysID, double L0) {
        this.cct = null;
        this.cct = new CCoordTrans(sysID, L0);
    }

    public Trans(int sysID, double L0, double FE) {
        this.cct = null;
        this.cct = new CCoordTrans(sysID, L0, FE);
    }

    public double[] doTrans(double l, double b) {
        CCoordTrans trans = new CCoordTrans(CCoordParam.COORDSYSTYPE_BEIJING54);
        return trans.getProjectCoord(l, b);
    }
}
