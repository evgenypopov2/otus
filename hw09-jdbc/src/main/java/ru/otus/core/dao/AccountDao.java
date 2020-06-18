package ru.otus.core.dao;

import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findById(long id);

    long insert(Account user);

    //void updateAccount(Account user);
    //void insertOrUpdate(Account user);

    SessionManager getSessionManager();
}
