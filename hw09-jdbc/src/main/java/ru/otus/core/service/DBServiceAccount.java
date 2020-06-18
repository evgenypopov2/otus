package ru.otus.core.service;

import ru.otus.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

    long save(Account account);

    Optional<Account> get(long id);
}
