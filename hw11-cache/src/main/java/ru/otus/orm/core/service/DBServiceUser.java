package ru.otus.orm.core.service;

import ru.otus.orm.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);
    User getUserWithCache(long id);

    void deleteUser(User user);
}
