package ru.otus.messagesystem.message;

public enum MessageType {
    USER_DATA("UserData"),
    GET_USER_DATA("GetUserData"),
    GET_USERS_DATA("GetUsersData"),
    PUT_USER_DATA("PutUserData");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
