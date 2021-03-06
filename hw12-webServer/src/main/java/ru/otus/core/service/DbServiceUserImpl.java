package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(user);
                long userId = user.getId();
                sessionManager.commitSession();

                logger.info("created user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return userDao.findById(id);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public void deleteUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.deleteUser(user);
                logger.info("user deleted: {}", user);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return userDao.findByLogin(login);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findRandomUser() {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return userDao.findRandomUser();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getUserList() {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return userDao.getUserList();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Collections.emptyList();
        }
    }
}
