package ru.otus.handlers;

import org.springframework.stereotype.Service;
import ru.otus.core.model.Address;
import ru.otus.core.model.User;
import ru.otus.dto.ResultDataTypeUserList;
import ru.otus.dto.UserDto;
import ru.otus.core.service.DBServiceUser;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.messagesystem.message.MessageType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GetUserDataRequestHandler implements RequestHandler<UserDto> {

    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";

    private final DBServiceUser dbServiceUser;
    private final Map<String, Function<Message, ResultDataType>> messageTypeActions = new ConcurrentHashMap<>();

    public GetUserDataRequestHandler(
            DBServiceUser dbServiceUser
            , MessageSystem messageSystem
            , CallbackRegistry callbackRegistry
    ) {
        this.dbServiceUser = dbServiceUser;

        messageTypeActions.put(MessageType.GET_USER_DATA.getName(), this::getUserData);
        messageTypeActions.put(MessageType.GET_USERS_DATA.getName(), this::getUserList);
        messageTypeActions.put(MessageType.PUT_USER_DATA.getName(), this::putUserData);

        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.GET_USER_DATA, this);
        requestHandlerDatabaseStore.addHandler(MessageType.GET_USERS_DATA, this);
        requestHandlerDatabaseStore.addHandler(MessageType.PUT_USER_DATA, this);

        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);
    }

    @Override
    public Optional<Message> handle(Message msg) {
        Function<Message, ResultDataType> action = messageTypeActions.get(msg.getType());
        if (action != null) {
            return Optional.of(MessageBuilder.buildReplyMessage(msg, action.apply(msg)));
        }
        return Optional.empty();
    }

    private UserDto getUserData(Message msg) {
        return dbServiceUser.getUser(MessageHelper.getPayload(msg)).map(this::userDtoFromUser).orElse(null);
    }

    private ResultDataTypeUserList getUserList(Message msg) {
        return new ResultDataTypeUserList(dbServiceUser.getUserList().stream().map(this::userDtoFromUser).collect(Collectors.toList()));
    }

    private UserDto putUserData(Message msg) {
        UserDto userDto = MessageHelper.getPayload(msg);
        User user = new User(userDto.getName(), userDto.getLogin(), userDto.getPassword());
        user.setAddress(new Address(userDto.getAddress(), user));
        return userDtoFromUser(dbServiceUser.saveUser(user));
    }

    private UserDto userDtoFromUser(User user) {
        return new UserDto(user.getId(), user.getName(), user.getLogin(), user.getPassword(),
                user.getAddress() != null ? user.getAddress().getStreet() : "");
    }
}
