package com.xmrbi.warehouse.data.entity.check;

import java.io.Serializable;
import java.util.List;

public class CheckStroeDeviceItem implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 帐面数量
     */
    private java.lang.Double bookValue;

    /**
     * 实际数量
     */
    private java.lang.Double factNum;

    /**
     * 货架名
     */
    private String drawerNames;
    /**
     * 货架id
     */
    private String drawerIds;

    /**
     * 构建名称
     */
    private String componentName;
    /**
     * 设备id
     */
    private Long deviceId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 型号名称
     */
    private String modelName;

    /**
     * 手持枪是否盘点标记（1为盘点完成,2盘点中）
     */
    private String isCheck;
    /**
     * 记录app实时盘点的数量
     */
    private Integer appNum;
    /**
     * 记录app实时盘点的rfid
     */
    private String appRfid;

    /**
     * 记录app实时盘点的盈亏
     */
    private Double profitLoss;

    /**
     * 盘点单id
     */
    private long checkStoreDeviceId;
    /**
     * 是否可以自动盘点
     */
    private boolean isAuto;
    /**
     * 可自动盘点的标签数
     */
    private int autoCount;

    /**
     * 该盘点单明细对应的盘点单rfid明细
     */
    private List<CheckStoreRfid> lstCheckStoreRfids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBookValue() {
        return bookValue;
    }

    public void setBookValue(Double bookValue) {
        this.bookValue = bookValue;
    }

    public Double getFactNum() {
        return factNum;
    }

    public void setFactNum(Double factNum) {
        this.factNum = factNum;
    }

    public String getDrawerNames() {
        return drawerNames;
    }

    public void setDrawerNames(String drawerNames) {
        this.drawerNames = drawerNames;
    }

    public String getDrawerIds() {
        return drawerIds;
    }

    public void setDrawerIds(String drawerIds) {
        this.drawerIds = drawerIds;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getAppNum() {
        return appNum;
    }

    public void setAppNum(Integer appNum) {
        this.appNum = appNum;
    }

    public String getAppRfid() {
        return appRfid;
    }

    public void setAppRfid(String appRfid) {
        this.appRfid = appRfid;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public long getCheckStoreDeviceId() {
        return checkStoreDeviceId;
    }

    public void setCheckStoreDeviceId(long checkStoreDeviceId) {
        this.checkStoreDeviceId = checkStoreDeviceId;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    public int getAutoCount() {
        return autoCount;
    }

    public void setAutoCount(int autoCount) {
        this.autoCount = autoCount;
    }

    public List<CheckStoreRfid> getLstCheckStoreRfids() {
        return lstCheckStoreRfids;
    }

    public void setLstCheckStoreRfids(List<CheckStoreRfid> lstCheckStoreRfids) {
        this.lstCheckStoreRfids = lstCheckStoreRfids;
    }
}
