package ru.otus.orm.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.orm.core.dao.UserDao;
import ru.otus.orm.core.model.User;

import java.util.Optional;

public class DbServiceUserImplWithCache extends DbServiceUserImpl {

    private static final String KEY_PREFIX = "key";

    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImplWithCache.class);
    protected final HwCache<String, User> myCache;

    public DbServiceUserImplWithCache(UserDao userDao, HwCache<String, User> myCache) {
        super(userDao);
        this.myCache = myCache;
    }

    public User getUserWithCache(long id) {
        String key = KEY_PREFIX + id;
        return Optional.ofNullable(myCache.get(key))
                .orElseGet(() -> super.getUser(id).map(user -> {
                    myCache.put(key, user);
                    return user;
                }).orElse(null));
    }

    public void deleteUserWithCache(User user) {
        super.deleteUser(user);
        myCache.remove(KEY_PREFIX + user.getId());
    }
}
