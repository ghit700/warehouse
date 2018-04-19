package com.xmrbi.warehouse.data.entity.main;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wzn on 2018/4/17.
 */
@Entity
public class Useunit {
    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 租户
     */
    private Long lesseeId;

    /**
     * 租户对应的仓库（作为解析使用）
     */
    @Transient
    private List<StoreHouse> storeHouses;

    @Generated(hash = 1982313441)
    public Useunit(Long id, String name, Long lesseeId) {
        this.id = id;
        this.name = name;
        this.lesseeId = lesseeId;
    }

    @Generated(hash = 1960778078)
    public Useunit() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<StoreHouse> getStoreHouses() {
        return storeHouses;
    }

    public void setStoreHouses(List<StoreHouse> storeHouses) {
        this.storeHouses = storeHouses;
    }
}
