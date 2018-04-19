package com.xmrbi.warehouse.data.entity.main;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wzn on 2018/4/17.
 */
@Entity
public class StoreHouseAioConfig {
    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 仓库ID
     */
    private Long storeHouseId;

    /**
     * 仓库名称
     */
    private String storeHouseName;

    /**
     * 租户
     */
    private Long lesseeId;

    /**
     * 门禁-开
     */
    private Integer entranceOpen;

    /**
     * 门禁-关
     */
    private Integer entranceClose;

    /**
     * 考勤机地址
     */
    private String fingerprintAddress;

    /**
     * 硬盘录像机ip
     */
    private String vcrIp;

    /**
     * 硬盘录像机端口号
     */
    private Integer vcrPort;

    /**
     * 硬盘录像机登录用户名
     */
    private String vcrUsername;

    /**
     * 硬盘录像机登录密码
     */
    private String vcrPassword;

    /**
     * 关键摄像机地址
     */
    private String vcrPrimaryAddress;

    /**
     * RFID读写器ip
     */
    private String rfidIp;

    /**
     * RFID读写器端口号
     */
    private Integer rfidPort;

    /**
     * 打印机串口名称
     */
    private String printSerialName;

    /**
     * 打印机波特率
     */
    private String printBautRate;

    /**
     * 灯控串口名称
     */
    private String lightSerialName;

    /**
     * 灯控波特率
     */
    private String lightBautRate;

    /**
     * 门禁串口名称
     */
    private String entranceSerialName;

    /**
     * 门禁波特率
     */
    private String entranceBautRate;

    @Generated(hash = 217289313)
    public StoreHouseAioConfig(Long id, Long storeHouseId, String storeHouseName,
            Long lesseeId, Integer entranceOpen, Integer entranceClose,
            String fingerprintAddress, String vcrIp, Integer vcrPort,
            String vcrUsername, String vcrPassword, String vcrPrimaryAddress,
            String rfidIp, Integer rfidPort, String printSerialName,
            String printBautRate, String lightSerialName, String lightBautRate,
            String entranceSerialName, String entranceBautRate) {
        this.id = id;
        this.storeHouseId = storeHouseId;
        this.storeHouseName = storeHouseName;
        this.lesseeId = lesseeId;
        this.entranceOpen = entranceOpen;
        this.entranceClose = entranceClose;
        this.fingerprintAddress = fingerprintAddress;
        this.vcrIp = vcrIp;
        this.vcrPort = vcrPort;
        this.vcrUsername = vcrUsername;
        this.vcrPassword = vcrPassword;
        this.vcrPrimaryAddress = vcrPrimaryAddress;
        this.rfidIp = rfidIp;
        this.rfidPort = rfidPort;
        this.printSerialName = printSerialName;
        this.printBautRate = printBautRate;
        this.lightSerialName = lightSerialName;
        this.lightBautRate = lightBautRate;
        this.entranceSerialName = entranceSerialName;
        this.entranceBautRate = entranceBautRate;
    }

    @Generated(hash = 1197133150)
    public StoreHouseAioConfig() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreHouseId() {
        return this.storeHouseId;
    }

    public void setStoreHouseId(Long storeHouseId) {
        this.storeHouseId = storeHouseId;
    }

    public String getStoreHouseName() {
        return this.storeHouseName;
    }

    public void setStoreHouseName(String storeHouseName) {
        this.storeHouseName = storeHouseName;
    }

    public Long getLesseeId() {
        return this.lesseeId;
    }

    public void setLesseeId(Long lesseeId) {
        this.lesseeId = lesseeId;
    }

    public Integer getEntranceOpen() {
        return this.entranceOpen;
    }

    public void setEntranceOpen(Integer entranceOpen) {
        this.entranceOpen = entranceOpen;
    }

    public Integer getEntranceClose() {
        return this.entranceClose;
    }

    public void setEntranceClose(Integer entranceClose) {
        this.entranceClose = entranceClose;
    }

    public String getFingerprintAddress() {
        return this.fingerprintAddress;
    }

    public void setFingerprintAddress(String fingerprintAddress) {
        this.fingerprintAddress = fingerprintAddress;
    }

    public String getVcrIp() {
        return this.vcrIp;
    }

    public void setVcrIp(String vcrIp) {
        this.vcrIp = vcrIp;
    }

    public Integer getVcrPort() {
        return this.vcrPort;
    }

    public void setVcrPort(Integer vcrPort) {
        this.vcrPort = vcrPort;
    }

    public String getVcrUsername() {
        return this.vcrUsername;
    }

    public void setVcrUsername(String vcrUsername) {
        this.vcrUsername = vcrUsername;
    }

    public String getVcrPassword() {
        return this.vcrPassword;
    }

    public void setVcrPassword(String vcrPassword) {
        this.vcrPassword = vcrPassword;
    }

    public String getVcrPrimaryAddress() {
        return this.vcrPrimaryAddress;
    }

    public void setVcrPrimaryAddress(String vcrPrimaryAddress) {
        this.vcrPrimaryAddress = vcrPrimaryAddress;
    }

    public String getRfidIp() {
        return this.rfidIp;
    }

    public void setRfidIp(String rfidIp) {
        this.rfidIp = rfidIp;
    }

    public Integer getRfidPort() {
        return this.rfidPort;
    }

    public void setRfidPort(Integer rfidPort) {
        this.rfidPort = rfidPort;
    }

    public String getPrintSerialName() {
        return this.printSerialName;
    }

    public void setPrintSerialName(String printSerialName) {
        this.printSerialName = printSerialName;
    }

    public String getPrintBautRate() {
        return this.printBautRate;
    }

    public void setPrintBautRate(String printBautRate) {
        this.printBautRate = printBautRate;
    }

    public String getLightSerialName() {
        return this.lightSerialName;
    }

    public void setLightSerialName(String lightSerialName) {
        this.lightSerialName = lightSerialName;
    }

    public String getLightBautRate() {
        return this.lightBautRate;
    }

    public void setLightBautRate(String lightBautRate) {
        this.lightBautRate = lightBautRate;
    }

    public String getEntranceSerialName() {
        return this.entranceSerialName;
    }

    public void setEntranceSerialName(String entranceSerialName) {
        this.entranceSerialName = entranceSerialName;
    }

    public String getEntranceBautRate() {
        return this.entranceBautRate;
    }

    public void setEntranceBautRate(String entranceBautRate) {
        this.entranceBautRate = entranceBautRate;
    }
}
