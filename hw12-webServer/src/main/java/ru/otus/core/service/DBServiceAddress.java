package ru.otus.core.service;

import ru.otus.core.model.Address;

import java.util.Optional;

public interface DBServiceAddress {

    long saveAddress(Address address);

    Optional<Address> getAddress(long id);
}
