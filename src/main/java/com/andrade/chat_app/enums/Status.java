package com.andrade.chat_app.enums;

public enum Status {
    OFFLINE("Offline"),
    ONLINE("Online");

    private String valueStatus;

    Status(String value) {
        this.valueStatus = value;
    }

    public String getStatus() {
        return this.valueStatus;
    }
}
