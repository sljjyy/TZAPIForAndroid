package com.tianzunchina.android.api.util.trans;

public class CCoordParam {
	public static double[] a = new double[] { 6378245.0, 6378140.0, 6378137.0 };
    public static double[] b = new double[] { 6356863.0188, 6356755.2882, 6356752.3142 };
    public int coordSysID = COORDSYSTYPE_BEIJING54;
    public static int COORDSYSTYPE_BEIJING54 = 0;
    public static int COORDSYSTYPE_WGS84 = 2;
    public static int COORDSYSTYPE_XIAN80 = 1;

    public CCoordParam(int coordSysID)
    {
        this.coordSysID = coordSysID;
    }

    public double getA() {
        if ((this.coordSysID >= 0) && (this.coordSysID <= 2))
        {
            return a[this.coordSysID];
        }
        return 0.0;
    }

    public double getB() {
        if ((this.coordSysID >= 0) && (this.coordSysID <= 2))
        {
            return b[this.coordSysID];
        }
        return 0.0;
    }
}
