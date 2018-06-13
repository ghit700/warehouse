package com.xmrbi.warehouse.data.entity.pick;


import com.xmrbi.warehouse.data.entity.deliver.Rfid;

import java.util.List;

/**
 * 新版的领料单明细
 * Created by wzn on 2018/5/18.
 */

public class PickListDetailNew {

    /**
     * id
     */
    private Long id;

    /**
     * 品名
     * device.model.component.name
     */
    private String componentName;

    /**
     * 品牌：(规格=品牌+型号)
     * device.model.brand.name
     */
    private String brandName;

    /**
     * 型号：(规格=品牌+型号)
     * device.model.name
     */
    private String modelName;

    /**
     * 单位
     * device.unit
     */
    private String unit;

    /**
     * 请领数量
     */
    private Integer requestAmount;

    /**
     * 存放货架
     */
    private String drawerName;
    /**
     * 货架id
     */
    private String drawerIds;

    /**
     * 单价
     */
    private Double price;

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备单价
     * device.price
     */
    private Double devicePrice;

    /**
     * 实领数量
     */
    private Integer actualAmount;

    /**
     * 租户id
     */
    private Long lesseeId;

    /**
     * 库存设备id
     */
    private Long storeDeviceId;

    /**
     * 已关联RFID
     */
    private String rfid;

    /**
     * 分配RFID
     * rfid.code
     */
    private String rfidCode;

    /**
     * 数量
     * rfid.amount
     */
    private Integer amount;

    /**
     * 是否已关联(RFID关联)
     *
     * @Transient
     */
    private boolean isRelation;
    /**
     * 如果有多张rfid就要包含多个rfid
     */
    private List<Rfid> lstRfids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(Integer requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Double getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(Double devicePrice) {
        this.devicePrice = devicePrice;
    }

    public Integer getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Integer actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Long getLesseeId() {
        return lesseeId;
    }

    public void setLesseeId(Long lesseeId) {
        this.lesseeId = lesseeId;
    }

    public Long getStoreDeviceId() {
        return storeDeviceId;
    }

    public void setStoreDeviceId(Long storeDeviceId) {
        this.storeDeviceId = storeDeviceId;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean getRelation() {
        return isRelation;
    }

    public void setRelation(boolean relation) {
        isRelation = relation;
    }

    public String getDrawerIds() {
        return drawerIds;
    }

    public void setDrawerIds(String drawerIds) {
        this.drawerIds = drawerIds;
    }

    public boolean isRelation() {
        return isRelation;
    }

    public List<Rfid> getLstRfids() {
        return lstRfids;
    }

    public void setLstRfids(List<Rfid> lstRfids) {
        this.lstRfids = lstRfids;
    }

    @Override
    public boolean equals(Object obj) {
        if (deviceId != null && obj.getClass().getName().contains("PickListDetailNew")) {
            return ((PickListDetailNew) obj).getDeviceId().equals(deviceId);
        }
        return super.equals(obj);
    }
}
