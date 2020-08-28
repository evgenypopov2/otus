package ru.otus.messagesystem.client;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CallbackRegistryImpl implements CallbackRegistry {

    private final Map<CallbackId, MessageCallback<? extends ResultDataType>> callbackRegistry = new ConcurrentHashMap<>();

    @Override
    public void put(CallbackId id, MessageCallback<? extends ResultDataType> callback) {
        callbackRegistry.put(id, callback);
    }

    @Override
    public MessageCallback<? extends ResultDataType> getAndRemove(CallbackId id) {
        return callbackRegistry.remove(id);
    }
}
