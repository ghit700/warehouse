package com.xmrbi.warehouse.event;

import com.xmrbi.warehouse.data.entity.deliver.RfidSearchHistory;

/**
 * Created by wzn on 2018/4/22.
 */

public class RfidSearchHistoryEvent {
    private RfidSearchHistory history;

    public RfidSearchHistoryEvent(String content, int type) {
        this.history = new RfidSearchHistory();
        this.history.setContent(content);
        this.history.setType(type);
    }

    public RfidSearchHistoryEvent(RfidSearchHistory history) {
        this.history = history;
    }

    public RfidSearchHistory getHistory() {
        return history;
    }
}
