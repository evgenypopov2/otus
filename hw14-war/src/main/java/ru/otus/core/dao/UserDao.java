package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    Optional<User> insertUser(User user);

    Optional<User> updateUser(User user);

    Optional<User> insertOrUpdate(User user);

    void deleteUser(User user);

    SessionManager getSessionManager();

    Optional<User> findRandomUser();
    Optional<User> findByLogin(String login);
    List<User> getUserList();
}
