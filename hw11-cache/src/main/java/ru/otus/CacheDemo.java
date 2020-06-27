package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.orm.core.model.Address;
import ru.otus.orm.core.model.Phone;
import ru.otus.orm.core.model.User;
import ru.otus.orm.core.service.*;
import ru.otus.orm.hibernate.HibernateUtils;
import ru.otus.orm.hibernate.dao.UserDaoHibernate;
import ru.otus.orm.hibernate.sessionmanager.SessionManagerHibernate;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

public class CacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(CacheDemo.class);

    private DBServiceUser dbServiceUser;

    public static void main(String[] args) throws InterruptedException {
        CacheDemo demo = new CacheDemo();
        demo.runDemo();
    }

    private CacheDemo() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, Address.class, Phone.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        dbServiceUser = new DbServiceUserImpl(new UserDaoHibernate(sessionManager));
    }

    private void runDemo() throws InterruptedException {

        // cache init
        HwCache<String, User> myCache = new MyCache<>();
        HwListener<String, User> listener = new HwListener<String, User>() {
            @Override
            public void notify(String key, User value, String action) {
                logger.info("MyCache: key:{}, value:{}, action: {}", key, value, action);
            }
        };
        myCache.addListener(listener);

        // load random data
        long[] userIds = new long[10];
        IntStream.range(0, 10).forEach(val -> userIds[val] = dbServiceUser.saveUser(createRandomUser()));

        // without cache
        logger.info("=========== without cache ==============");
        for (long id : userIds) {
            Optional<User> optionalUser = dbServiceUser.getUser(id);
            logger.info("loaded user: {}", optionalUser.orElse(null));
        }

        // with cache
        // 1-st round for cache warming
        logger.info("=========== with cache - 1, cache warming ==============");
        for (long id : userIds) {
            User user = getWithCache(id, myCache);
            logger.info("loaded user: {}", user);
        }
        // 2-nd round for cache work demo
        logger.info("=========== with cache - 2, with warm cache ==============");
        for (long id : userIds) {
            User user = getWithCache(id, myCache);
            logger.info("loaded user: {}", user);
        }

        // get huge memory block
        int size = 1000;
        List<SoftReference<BigObject>> references = new ArrayList<>(size);
        for (int k = 0; k < size; k++) {
            references.add(new SoftReference<>(new BigObject()));
        }

        //System.gc();
        //Thread.sleep(1000);
        logger.info("after gc, cache size: {}", myCache.size());

        // 3-rd round for check cache clear
        logger.info("=========== with cache - 3, after cache clear ==============");
        for (long id : userIds) {
            User user = getWithCache(id, myCache);
            logger.info("loaded user: {}", user);
        }
    }

    private User getWithCache(long id, HwCache<String, User> myCache) {
        String key = "key" + id;
        User user = myCache.get(key);
        if (user == null) {
            Optional<User> userOptional = dbServiceUser.getUser(id);
            if (userOptional.isPresent()) {
                user = userOptional.get();
                myCache.put(key, user);
            }
        }
        return user;
    }

    private User createRandomUser() {
        User user = new User("User-" + UUID.randomUUID());
        user.setAddress(new Address("Address-" + UUID.randomUUID(), user));
        IntStream.range(0, makeRandomInt(2,6)).forEach(val ->
                user.addPhone(new Phone("Phone-" + UUID.randomUUID(), user)));
        return user;
    }

    public static int makeRandomInt(int min, int max) {
        return Math.round((float) Math.random() * (max - min)) + min;
    }

    static class BigObject {
        final byte[] array = new byte[1024 * 1024];
    }
}

