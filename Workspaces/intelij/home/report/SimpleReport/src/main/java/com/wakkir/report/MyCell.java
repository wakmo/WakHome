package com.wakkir.report;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 16/12/12
 * Time: 01:04
 * To change this template use File | Settings | File Templates.
 */
public class MyCell
{
    private int x;
    private int y;
    private int z;
    private int t;

    private Object data;
    private int type;

    //private CellStyle cellStyle=new CellStyle();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
