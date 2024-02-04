package com.thinkdiffai.futurelove.model;

import java.util.List;

public class EventVideoParent {
    private List<EventVideo> eventVideoList;

    public EventVideoParent(List<EventVideo> eventVideoList) {
        this.eventVideoList = eventVideoList;
    }

    public List<EventVideo> getEventVideoList() {
        return eventVideoList;
    }

    public void setEventVideoList(List<EventVideo> eventVideoList) {
        this.eventVideoList = eventVideoList;
    }
}
