package ru.otus.orm.core.dao;

import ru.otus.orm.core.model.Address;
import ru.otus.orm.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AddressDao {
    Optional<Address> findById(long id);

    long insertAddress(Address address);

    void updateAddress(Address address);

    void insertOrUpdate(Address address);

    SessionManager getSessionManager();
}
