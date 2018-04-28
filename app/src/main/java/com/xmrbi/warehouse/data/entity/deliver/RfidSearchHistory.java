package com.xmrbi.warehouse.data.entity.deliver;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by wzn on 2018/4/22.
 */
@Entity
public class RfidSearchHistory  {
    /**
     * 搜索的内容
     */
    private String content;

    @Generated(hash = 1406336774)
    public RfidSearchHistory(String content) {
        this.content = content;
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

}
