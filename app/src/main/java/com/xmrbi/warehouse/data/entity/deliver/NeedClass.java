package com.xmrbi.warehouse.data.entity.deliver;

/**
 * 需求类别（最后一级为具体货架）
 * Created by wzn on 2018/4/28.
 */

public class NeedClass {
    private double amount;
    private String unit;
    private int needId;
    private String assetCode;
    private String needClassName;
    private String drawerIds;
    private String name;
    private String model;
    private String drawerName;
    private Long rfidTime;
    private int deviceId;
    private String brand;


    public String getDrawerIds() {
        return drawerIds;
    }

    public void setDrawerIds(String drawerIds) {
        this.drawerIds = drawerIds;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

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

    public int getNeedId() {
        return needId;
    }

    public void setNeedId(int needId) {
        this.needId = needId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getNeedClassName() {
        return needClassName;
    }

    public void setNeedClassName(String needClassName) {
        this.needClassName = needClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public Long getRfidTime() {
        return rfidTime;
    }

    public void setRfidTime(Long rfidTime) {
        this.rfidTime = rfidTime;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return name;
    }
}
