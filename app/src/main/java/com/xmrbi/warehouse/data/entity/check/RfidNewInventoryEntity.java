package com.xmrbi.warehouse.data.entity.check;


public class RfidNewInventoryEntity {


    /**
     * 货架名
     */
    private String drawerName;
    /**
     * 盘点完成的数量
     */
    private int check;
    /**
     * 未盘点数量
     */
    private int noCheck;

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getNoCheck() {
        return noCheck;
    }

    public void setNoCheck(int noCheck) {
        this.noCheck = noCheck;
    }
}
