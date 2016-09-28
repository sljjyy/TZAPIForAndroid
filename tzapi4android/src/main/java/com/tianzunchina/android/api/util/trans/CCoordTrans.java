package com.tianzunchina.android.api.util.trans;

public class CCoordTrans {
	private double a;
    private double b;
    private double B;
    private int coordSysID;
    private double FE;
    public static int k0 = 1;
    private double L;
    private double L0;

    public CCoordTrans(int coordSysID) {
        this.L = 0.0;
        this.B = 0.0;
        this.a = 0.0;
        this.b = 0.0;
        this.L0 = 0.0;
        this.coordSysID = 1;
        this.FE = 500000.0;
        this.coordSysID = coordSysID;
        CCoordParam param = new CCoordParam(coordSysID);
        this.a = param.getA();
        this.b = param.getB();
        this.L0 = 2.0943951023931953;
    }

    public CCoordTrans(int coordSysID, double l0) {
        this.L = 0.0;
        this.B = 0.0;
        this.a = 0.0;
        this.b = 0.0;
        this.L0 = 0.0;
        this.coordSysID = 1;
        this.FE = 500000.0;
        this.coordSysID = coordSysID;
        CCoordParam param = new CCoordParam(coordSysID);
        this.a = param.getA();
        this.b = param.getB();
        this.L0 = (l0 / 180.0) * 3.1415926535897931;
    }

    public CCoordTrans(int coordSysID, double l0, double FE) {
        this.L = 0.0;
        this.B = 0.0;
        this.a = 0.0;
        this.b = 0.0;
        this.L0 = 0.0;
        this.coordSysID = 1;
        this.FE = 500000.0;
        this.coordSysID = coordSysID;
        CCoordParam param = new CCoordParam(coordSysID);
        this.a = param.getA();
        this.b = param.getB();
        this.L0 = (l0 / 180.0) * 3.1415926535897931;
    }

    public double getA()
    {
        return ((this.L - this.L0) * Math.cos(this.B));
    }

    public double getC() {
        return (Math.pow(this.getSEccentricity(), 2.0) * Math.pow(Math.cos(this.B), 2.0));
    }

    public double getEccentricity()
    {
        return Math.sqrt(1.0 - Math.pow(this.b / this.a, 2.0));
    }

    public double getFlattening()
    {
        return ((this.a - this.b) / this.a);
    }

    public double getM() {
        double x = this.getEccentricity();
        double num2 = Math.pow(x, 2.0) / 4.0;
        double num3 = (3.0 * Math.pow(x, 4.0)) / 64.0;
        double num4 = (5.0 * Math.pow(x, 6.0)) / 256.0;
        double num5 = (((1.0 - (Math.pow(x, 2.0) / 4.0)) - ((3.0 * Math.pow(x, 4.0)) / 64.0)) - ((5.0 * Math.pow(x, 6.0)) / 256.0)) * this.B;
        double num6 = ((((3.0 * Math.pow(x, 2.0)) / 8.0) + ((3.0 * Math.pow(x, 4.0)) / 32.0)) + ((45.0 * Math.pow(x, 6.0)) / 1024.0)) * Math.sin(2.0 * this.B);
        double num7 = (((15.0 * Math.pow(x, 4.0)) / 256.0) + ((45.0 * Math.pow(x, 6.0)) / 1024.0)) * Math.sin(4.0 * this.B);
        double num8 = ((35.0 * Math.pow(x, 6.0)) / 3072.0) * Math.sin(6.0 * this.B);
        return (this.a * (((num5 - num6) + num7) - num8));
    }

    private double getMeridian(double val2) {
        if (val2 < 0.0)
        {
            return -1.0;
        }
        double num = (val2 * 180.0) / 3.1415926535897931;
        int num2 = (int) num;
        double num3 = num - num2;
        int num4 = num2 / 3;
        num3 += num2 % 3;
        num4++;
        return (num4 * 3);
    }

    public double getN() {
        double x = this.getEccentricity();
        return (this.a / Math.sqrt(1.0 - (Math.pow(x, 2.0) * Math.pow(Math.sin(this.B), 2.0))));
    }

    public double[] getProjectCoord(double lValue, double bValue) {
        double d = lValue;
        double num2 = bValue;
        double num3 = this.getRad(d);
        double num4 = this.getRad(num2);
        this.L0 = (this.getMeridian(num3) / 180.0) * 3.1415926535897931;
        this.L = num3;
        this.B = num4;
        double num5 = this.getM();
        double x = this.getA();
        double num7 = this.getN();
        double num8 = this.getT();
        double num9 = this.getC();
        double fE = num5;
        double num11 = Math.pow(x, 2.0) / 2.0;
        double num12 = ((((5.0 - num8) + (9.0 * num9)) + (4.0 * Math.pow(num9, 2.0))) * Math.pow(x, 4.0)) / 24.0;
        double num13 = (num7 * Math.tan(this.B)) * ((Math.pow(x, 2.0) / 2.0) + (((((5.0 - num8) + (9.0 * num9)) + (4.0 * Math.pow(num9, 2.0))) * Math.pow(x, 4.0)) / 24.0));
        double num14 = (((((61.0 - (58.0 * num8)) + Math.pow(num8, 2.0)) + (270.0 * num9)) - ((330.0 * num8) * num9)) * Math.pow(x, 6.0)) / 720.0;
        double num15 = (fE + num13) + num14;
        fE = this.FE;
        num13 = x;
        num14 = (((1.0 - num8) + num9) * Math.pow(x, 3.0)) / 6.0;
        double num16 = (((((5.0 - (18.0 * num8)) - Math.pow(num8, 2.0)) + (14.0 * num9)) - ((58.0 * num8) * num9)) * Math.pow(x, 5.0)) / 120.0;
        double num17 = fE + ((k0 * num7) * ((num13 + num14) + num16));
        double[] numArray = new double[2];
        numArray[1] = num15;
        numArray[0] = num17;
        return numArray;
    }

    public double getRad(double d)
    {
        return ((d / 180.0) * 3.1415926535897931);
    }

    public double getSEccentricity() {
        double num = (this.a * 1.0) / this.b;
        return Math.sqrt((num * num) - 1.0);
    }

    public double getT()
    {
        return Math.pow(Math.tan(this.B), 2.0);
    }

    private double[] trans(double xOld, double yOld) {
        double num = 18.6567;
        double num2 = -17.936000000000007;
        double num3 = 0.99999997;
        double d = 2.8763510886547659E-06;
        double num5 = num + (num3 * ((xOld * Math.cos(d)) - (yOld * Math.sin(d))));
        double num6 = num2 + (num3 * ((xOld * Math.sin(d)) + (yOld * Math.cos(d))));
        double[] numArray = new double[2];
        numArray[1] = num5;
        numArray[0] = num6;
        return numArray;
    }

    public double transDFMtoDegree(double dfm) {
        double num = Math.floor(dfm);
        double num2 = ((1.0 * (Math.floor(dfm * 100.0) - (num * 100.0))) * 1.0) / 60.0;
        double num3 = ((dfm * 10000.0) - (Math.floor(dfm * 100.0) * 100.0)) / 3600.0;
        return ((num + num2) + num3);
    }
}
