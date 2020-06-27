package ru.otus.orm.core.dao;

import ru.otus.orm.core.model.Phone;
import ru.otus.orm.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> findById(long id);

    long insertPhone(Phone phone);

    void updatePhone(Phone phone);

    void insertOrUpdate(Phone phone);

    SessionManager getSessionManager();
}
