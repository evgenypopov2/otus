package ru.otus.orm.core.service;

import ru.otus.orm.core.model.Address;

import java.util.Optional;

public interface DBServiceAddress {

    long saveAddress(Address address);

    Optional<Address> getAddress(long id);
}
