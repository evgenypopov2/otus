package ru.otus.dto;

import ru.otus.messagesystem.client.ResultDataType;

public class UserDto extends ResultDataType {
    private long id;
    private String name;
    private String login;
    private String password;
    private String address;

    public UserDto() {}
    public UserDto(long id, String name, String login, String password, String address) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
