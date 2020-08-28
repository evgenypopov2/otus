package ru.otus.messagesystem.message;

import ru.otus.messagesystem.client.CallbackId;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.UUID;

public class MessageBuilder {
    private static final Message VOID_MESSAGE =
            new Message(new MessageId(UUID.randomUUID().toString()), null, null, null,
                    null, "voidTechnicalMessage", new byte[1],  null);

    private MessageBuilder() {
    }

    public static Message getVoidMessage() {
        return VOID_MESSAGE;
    }

    public static <T extends ResultDataType> Message buildMessage(UUID requestId, String from, String to, MessageId sourceMessageId,
                                                                  T data, MessageType msgType) {
        return buildMessage(requestId, from, to, sourceMessageId, data, msgType, null);
    }

    public static <T extends ResultDataType> Message buildReplyMessage(Message message, T data) {
        return buildMessage(message.getRequestId(), message.getTo(), message.getFrom(), message.getId(), data,
                MessageType.USER_DATA, message.getCallbackId());
    }

    private static <T extends ResultDataType> Message buildMessage(UUID requestId, String from, String to, MessageId sourceMessageId,
                                                                   T data, MessageType msgType, CallbackId callbackId) {
        String id = UUID.randomUUID().toString();
        return new Message(new MessageId(id), requestId, from, to, sourceMessageId, msgType.getName(),
                Serializers.serialize(data), callbackId == null ? new CallbackId(id) : callbackId);
    }
}
