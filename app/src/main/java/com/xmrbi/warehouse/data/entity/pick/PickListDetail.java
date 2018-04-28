package com.xmrbi.warehouse.data.entity.pick;


import java.io.Serializable;

public class PickListDetail implements Serializable {

    private String rfid;// 扫描成功后填写
    private String mate;//在领料单页面标记是否已经全部匹配完
    private String model;
    private String num;
    private String name;
    private String brand;
    private int deviceId;
    private String storage;
    private String rfidTag;// 用于比对

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMate() {
        return mate;
    }

    public void setMate(String mate) {
        this.mate = mate;
    }

}
