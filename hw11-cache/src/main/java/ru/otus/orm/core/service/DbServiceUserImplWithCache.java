package ru.otus.orm.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.orm.core.model.User;

import java.util.Optional;

public class DbServiceUserImplWithCache implements DBServiceUser {

    private static final String KEY_PREFIX = "key";

    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImplWithCache.class);
    protected final HwCache<String, User> myCache;
    private final DBServiceUser dbServiceUser;


    public DbServiceUserImplWithCache(DBServiceUser dbServiceUser, HwCache<String, User> myCache) {
        this.myCache = myCache;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public long saveUser(User user) {
        return dbServiceUser.saveUser(user);
    }

    @Override
    public Optional<User> getUser(long id) {
        String key = KEY_PREFIX + id;
        return Optional.ofNullable(Optional.ofNullable(myCache.get(key))
                .orElseGet(() -> dbServiceUser.getUser(id).map(user -> {
                    myCache.put(key, user);
                    return user;
                }).orElse(null)));
    }

    @Override
    public void deleteUser(User user) {
        dbServiceUser.deleteUser(user);
        myCache.remove(KEY_PREFIX + user.getId());
    }
}
