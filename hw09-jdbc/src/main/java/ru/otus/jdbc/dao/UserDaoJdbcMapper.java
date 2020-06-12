package ru.otus.jdbc.dao;

import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.util.Optional;

public class UserDaoJdbcMapper implements UserDao {

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<User> jdbcMapper;

    public UserDaoJdbcMapper(SessionManagerJdbc sessionManager, DbExecutorImpl<User> dbExecutor) throws NoSuchMethodException {
        this.sessionManager = sessionManager;
        jdbcMapper = new JdbcMapperImpl<>(User.class, sessionManager, dbExecutor);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.of(jdbcMapper.findById(id));
    }

    @Override
    public long insert(User user) {
        jdbcMapper.insert(user);
        return jdbcMapper.findById(user.getId()).getId();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
