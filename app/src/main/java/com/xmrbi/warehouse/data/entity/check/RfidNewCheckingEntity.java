package com.xmrbi.warehouse.data.entity.check;

import java.io.Serializable;
import java.util.List;

public class RfidNewCheckingEntity implements Serializable {

    /**
     * data : [{"factNum":null,"bookValue":10,"deviceNum":20,"csrID":1371,"name":"五孔插座","rfid":"0000000AAA20161200000685","drawerName":"开关A1","model":"86型10A","csriID":null,"brand":"公牛","deviceId":293225},{"factNum":null,"bookValue":10,"deviceNum":20,"csrID":1372,"name":"五孔插座","rfid":"0000000AAA20161200000686","drawerName":"开关A1","model":"86型10A","csriID":null,"brand":"公牛","deviceId":293225},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1383,"name":"时控","rfid":"0000000AAA20161200000688","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1384,"name":"时控","rfid":"0000000AAA20161200000696","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1385,"name":"时控","rfid":"0000000AAA20161200000695","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1386,"name":"时控","rfid":"0000000AAA20161200000694","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1387,"name":"时控","rfid":"0000000AAA20161200000693","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1388,"name":"时控","rfid":"0000000AAA20161200000692","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1389,"name":"时控","rfid":"0000000AAA20161200000690","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1390,"name":"时控","rfid":"0000000AAA20161200000689","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1391,"name":"时控","rfid":"0000000AAA20161200000691","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223},{"factNum":null,"bookValue":1,"deviceNum":10,"csrID":1392,"name":"时控","rfid":"0000000AAA20161200000687","drawerName":"开关A1","model":"APT-8S  220v  50/60HZ","csriID":null,"brand":"ANLY","deviceId":293223}]
     * success : true
     * rfidCount : 12
     * noRfidCount : 0
     */

    private boolean success;
    private int rfidCount;
    private int noRfidCount;
    private List<DataBean> data;
    private int checkCount;
    private int unCheckCount;
    private String errorMsg;


    public int getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }

    public int getUnCheckCount() {
        return unCheckCount;
    }

    public void setUnCheckCount(int unCheckCount) {
        this.unCheckCount = unCheckCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getRfidCount() {
        return rfidCount;
    }

    public void setRfidCount(int rfidCount) {
        this.rfidCount = rfidCount;
    }

    public int getNoRfidCount() {
        return noRfidCount;
    }

    public void setNoRfidCount(int noRfidCount) {
        this.noRfidCount = noRfidCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean implements Serializable {
        /**
         * factNum : null
         * bookValue : 10.0
         * deviceNum : 20.0
         * csrID : 1371
         * name : 五孔插座
         * rfid : 0000000AAA20161200000685
         * drawerName : 开关A1
         * model : 86型10A
         * csriID : null
         * brand : 公牛
         * deviceId : 293225
         */

        private Double factNum;
        private Double bookValue;
        private Double deviceNum;
        private int csrID;
        private String name;
        private String rfid;
        private String drawerName;
        private String model;
        private String csriID;
        private String brand;
        private int deviceId;
        private String factNums;
        private String deviceNums;
        private String bookValues;
        private String rfids;
        private String csrIDs;
        private Boolean isScan;


        public Boolean getIsScan() {
            return isScan;
        }

        public void setIsScan(Boolean isScan) {
            this.isScan = isScan;
        }

        public Double getFactNum() {
            return factNum;
        }

        public void setFactNum(Double factNum) {
            this.factNum = factNum;
        }


        public Double getBookValue() {
            return bookValue;
        }

        public void setBookValue(Double bookValue) {
            this.bookValue = bookValue;
        }

        public Double getDeviceNum() {
            return deviceNum;
        }

        public void setDeviceNum(Double deviceNum) {
            this.deviceNum = deviceNum;
        }

        public int getCsrID() {
            return csrID;
        }

        public void setCsrID(int csrID) {
            this.csrID = csrID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRfid() {
            return rfid;
        }

        public void setRfid(String rfid) {
            this.rfid = rfid;
        }

        public String getDrawerName() {
            return drawerName;
        }

        public void setDrawerName(String drawerName) {
            this.drawerName = drawerName;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getCsriID() {
            return csriID;
        }

        public void setCsriID(String csriID) {
            this.csriID = csriID;
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

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public String getFactNums() {
            return factNums;
        }

        public void setFactNums(String factNums) {
            this.factNums = factNums;
        }

        public String getDeviceNums() {
            return deviceNums;
        }

        public void setDeviceNums(String deviceNums) {
            this.deviceNums = deviceNums;
        }

        public String getBookValues() {
            return bookValues;
        }

        public void setBookValues(String bookValues) {
            this.bookValues = bookValues;
        }

        public String getRfids() {
            return rfids;
        }

        public void setRfids(String rfids) {
            this.rfids = rfids;
        }

        public String getCsrIDs() {
            return csrIDs;
        }

        public void setCsrIDs(String csrIDs) {
            this.csrIDs = csrIDs;
        }

        public Boolean getScan() {
            return isScan;
        }

        public void setScan(Boolean scan) {
            isScan = scan;
        }
    }
}
