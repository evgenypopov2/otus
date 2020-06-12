package ru.otus.jdbc.dao;

import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.util.Optional;

public class AccountDaoJdbcMapper implements AccountDao {

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<Account> jdbcMapper;

    public AccountDaoJdbcMapper(SessionManagerJdbc sessionManager, DbExecutorImpl<Account> dbExecutor) throws NoSuchMethodException {
        this.sessionManager = sessionManager;
        jdbcMapper = new JdbcMapperImpl<>(Account.class, sessionManager, dbExecutor);
    }

    @Override
    public Optional<Account> findById(long id) {
        return Optional.of(jdbcMapper.findById(id));
    }

    @Override
    public long insert(Account account) {
        jdbcMapper.insert(account);
        return jdbcMapper.findById(account.getNo()).getNo();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
