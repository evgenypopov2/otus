package ru.otus.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.core.service.DataLoadService;
import ru.otus.dto.ResultDataTypeUserList;
import ru.otus.dto.UserDto;
import ru.otus.handlers.GetUserDataResponseHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;


@Controller
public class UserController {

    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";

    private SimpMessagingTemplate template;

    private final MsClient frontendMsClient;

    public UserController(
            DataLoadService dataLoadService
            ,MessageSystem messageSystem
            ,CallbackRegistry callbackRegistry
            ,GetUserDataResponseHandler handler
            ,SimpMessagingTemplate template
    ) {
        this.template = template;

        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, handler);
        frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerFrontendStore, callbackRegistry);
        messageSystem.addClient(frontendMsClient);

        dataLoadService.loadData();
    }

    @GetMapping({"/"})
    public String userListView() {
        return "users";
    }

    @GetMapping("/users/create")
    public String userCreateView() {
        return "user";
    }

    @MessageMapping("/list")
    public void getUsers(String message) {
        Message outMsg = frontendMsClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, null,
                MessageType.GET_USERS_DATA, this::returnUserList);
        frontendMsClient.sendMessage(outMsg);
    }

    @MessageMapping("/save")
    public void saveUser(UserDto userDto) {
        Message outMsg = frontendMsClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, userDto,
                MessageType.PUT_USER_DATA, this::returnSavedUser);
        frontendMsClient.sendMessage(outMsg);
    }

    public void returnUserList(ResultDataTypeUserList resultDataTypeUserList) {
        template.convertAndSend("/userlist", resultDataTypeUserList.getUserList());
    }

    public void returnSavedUser(UserDto userDto) {
        template.convertAndSend("/usersaved", userDto);
    }
}
