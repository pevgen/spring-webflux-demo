package com.example.demo.model;

public class DealEvent {

    private Long eventId;
    private String eventType;

    public DealEvent() {
    }

    public DealEvent(Long eventId, String eventType) {

        this.eventId = eventId;
        this.eventType = eventType;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
