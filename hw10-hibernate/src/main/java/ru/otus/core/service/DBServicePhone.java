package ru.otus.core.service;

import ru.otus.core.model.Phone;

import java.util.Optional;

public interface DBServicePhone {

    long savePhone(Phone phone);

    Optional<Phone> getPhone(long id);
}
