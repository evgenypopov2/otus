package ru.otus.messagesystem.client;

import java.io.Serializable;
import java.util.UUID;

public class ResultDataType implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID requestId;

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
}
