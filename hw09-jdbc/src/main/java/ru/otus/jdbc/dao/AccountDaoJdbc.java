package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.AccountDaoException;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static final Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutorImpl<Account> dbExecutor;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutorImpl<Account> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<Account> findById(long id) {
        try {
            return dbExecutor.executeSelect(getConnection(), "select no, type, rest from account where no = ?",
                    id, rs -> {
                        try {
                            if (rs.next()) {
                                return new Account(rs.getLong("no"), rs.getString("type"), rs.getDouble("rest"));
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insert(Account account) {
        try {
            return dbExecutor.executeInsert(getConnection(), "insert into account(type, rest) values (?,?)",
                    Arrays.asList(account.getType(), account.getRest()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
