package com.xmrbi.warehouse.data.entity.deliver;


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



    /**
     * 如果比较的是字符串直接和code比较
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().getSimpleName().equals("Rfid")){
            return this.code.equals(((Rfid)obj).code);
        }
        return super.equals(obj);
    }
}
