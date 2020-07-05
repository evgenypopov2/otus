package ru.otus.orm.core.service;

import ru.otus.orm.core.model.Phone;

import java.util.Optional;

public interface DBServicePhone {

    long savePhone(Phone phone);

    Optional<Phone> getPhone(long id);
}
