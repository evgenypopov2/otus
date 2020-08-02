package ru.otus.dto;

import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

public class ResultDataTypeUserList extends ResultDataType {
    private List<UserDto> userList;
    public ResultDataTypeUserList(List<UserDto> userList) {
        this.userList = userList;
    }
    public List<UserDto> getUserList() {
        return userList;
    }
}
