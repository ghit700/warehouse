package com.xmrbi.warehouse.data.entity.main;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wzn on 2018/4/17.
 */
@Entity
public class StoreHouse {
    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 单位
     */
    private Long useunitId;

    /**
     * 名称
     */
    private String name;

    /**
     * 租户
     */
    private Long lesseeId;

    /**
     * 是否配备RFID读写器
     */
    private Boolean isRfid;

    @Generated(hash = 1046270656)
    public StoreHouse(Long id, Long useunitId, String name, Long lesseeId,
            Boolean isRfid) {
        this.id = id;
        this.useunitId = useunitId;
        this.name = name;
        this.lesseeId = lesseeId;
        this.isRfid = isRfid;
    }

    @Generated(hash = 2046432707)
    public StoreHouse() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUseunitId() {
        return this.useunitId;
    }

    public void setUseunitId(Long useunitId) {
        this.useunitId = useunitId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLesseeId() {
        return this.lesseeId;
    }

    public void setLesseeId(Long lesseeId) {
        this.lesseeId = lesseeId;
    }

    public Boolean getIsRfid() {
        return this.isRfid;
    }

    public void setIsRfid(Boolean isRfid) {
        this.isRfid = isRfid;
    }

}
