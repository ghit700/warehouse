package com.xmrbi.warehouse.data.entity.check;


public class RfidNewInventoryEntity {


    /**
     * drawerName :
     * rfidUncheck : 17
     * rfidCheck : 0
     * noRfidCheck : 41
     * noRfidUncheck : 433
     */

    private String drawerName;
    private int rfidUncheck;
    private int rfidCheck;
    private int noRfidCheck;
    private int noRfidUncheck;

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public int getRfidUncheck() {
        return rfidUncheck;
    }

    public void setRfidUncheck(int rfidUncheck) {
        this.rfidUncheck = rfidUncheck;
    }

    public int getRfidCheck() {
        return rfidCheck;
    }

    public void setRfidCheck(int rfidCheck) {
        this.rfidCheck = rfidCheck;
    }

    public int getNoRfidCheck() {
        return noRfidCheck;
    }

    public void setNoRfidCheck(int noRfidCheck) {
        this.noRfidCheck = noRfidCheck;
    }

    public int getNoRfidUncheck() {
        return noRfidUncheck;
    }

    public void setNoRfidUncheck(int noRfidUncheck) {
        this.noRfidUncheck = noRfidUncheck;
    }
}
