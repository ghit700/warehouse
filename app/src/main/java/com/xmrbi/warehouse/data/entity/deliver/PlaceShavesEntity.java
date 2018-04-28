package com.xmrbi.warehouse.data.entity.deliver;

/**
 * 上架设备信息
 * Created by wzn on 2018/4/28.
 */

public class PlaceShavesEntity {
    private double amount;
    private String unit;
    private String model;
    private String drawerName;
    /**
     * 货架类型
     */
    private int needId;
    private String needClassName;
    private Long rfidTime;
    private String name;
    private String brand;
    private Object drawerIds;
    private String assetCode;
    private int deviceId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public int getNeedId() {
        return needId;
    }

    public void setNeedId(int needId) {
        this.needId = needId;
    }

    public String getNeedClassName() {
        return needClassName;
    }

    public void setNeedClassName(String needClassName) {
        this.needClassName = needClassName;
    }

    public Long getRfidTime() {
        return rfidTime;
    }

    public void setRfidTime(Long rfidTime) {
        this.rfidTime = rfidTime;
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

    public Object getDrawerIds() {
        return drawerIds;
    }

    public void setDrawerIds(Object drawerIds) {
        this.drawerIds = drawerIds;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
