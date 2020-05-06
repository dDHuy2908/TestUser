package com.ddhuy4298.test.models;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("message_id")
    private Integer messageId;

    public Message(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
