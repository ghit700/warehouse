package com.xmrbi.warehouse.data.entity.deliver;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by wzn on 2018/4/22.
 */
@Entity
public class RfidSearchHistory {
    /**
     * 搜索的内容
     */
    private String content;
    /**
     * 搜索内容的类型
     */
    private int type;

    @Generated(hash = 1873924810)
    public RfidSearchHistory(String content, int type) {
        this.content = content;
        this.type = type;
    }

    @Generated(hash = 1680113981)
    public RfidSearchHistory() {
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
