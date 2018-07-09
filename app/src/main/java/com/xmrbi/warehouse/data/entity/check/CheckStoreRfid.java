package com.xmrbi.warehouse.data.entity.check;

/**
 * 盘点单明细(rfid)
 * Created by wzn on 2018/7/5.
 */
public class CheckStoreRfid {
    /**
     * id
     */
    private Long id;

    /**
     * 数量
     */
    private java.lang.Double amount;
    /**
     * 实际数量
     */
    private java.lang.Double factAmount;

    /**
     *  设备对应的rfid的编码
     */
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getFactAmount() {
        return factAmount;
    }

    public void setFactAmount(Double factAmount) {
        this.factAmount = factAmount;
    }
}
