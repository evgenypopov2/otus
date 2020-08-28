package ru.otus.dto;

import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;
import java.util.UUID;

public class ResultDataTypeUserList extends ResultDataType {
    private List<UserDto> userList;
    public ResultDataTypeUserList(UUID requestId, List<UserDto> userList) {
        this.setRequestId(requestId);
        this.userList = userList;
    }
    public List<UserDto> getUserList() {
        return userList;
    }
}
