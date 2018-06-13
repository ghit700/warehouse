package com.xmrbi.warehouse.data.entity.deliver;


import com.speedata.libuhf.bean.Tag_Data;

/**
 * Created by Zwl on 2018/4/25.
 */

public class Rfid {
    /**
     * 编码
     */
    private String code;
    /**
     * 数量
     */
    private String amount;
    /**
     * 设备名
     */
    private String name;
    /**
     * 型号
     */
    private String model;

    /**
     * 货架名称
     */
    private String drawerNames;
    /**
     * 货架id
     */
    private String drawerIds;

    public Rfid() {
    }

    public Rfid(String code) {
        this.code = code;
    }

    public Rfid(String code, String amount) {
        this.code = code;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    /**
     * 如果比较的是字符串直接和code比较
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().getSimpleName().equals("Rfid")) {
            return this.code.equals(((Rfid) obj).code);
        }
        if (obj.getClass().getSigners().equals("Tag_Data")) {
            return this.code.equals(((Tag_Data) obj).epc);
        }
        return super.equals(obj);
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
}
