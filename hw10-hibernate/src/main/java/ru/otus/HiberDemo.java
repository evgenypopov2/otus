package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AddressDao;
import ru.otus.core.dao.PhoneDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.User;
import ru.otus.core.model.Phone;
import ru.otus.core.service.*;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.AddressDaoHibernate;
import ru.otus.hibernate.dao.PhoneDaoHibernate;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class HiberDemo {
    private static final Logger logger = LoggerFactory.getLogger(HiberDemo.class);

    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";
    private final SessionFactory sessionFactory;

    public static void main(String[] args) {
        HiberDemo demo = new HiberDemo();

        demo.entityExample();
    }

    private HiberDemo() {
        sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, Address.class, Phone.class);
    }

    private void entityExample() {
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        AddressDao addressDao = new AddressDaoHibernate(sessionManager);
        DBServiceAddress dbServiceAddress = new DbServiceAddressImpl(addressDao);

        PhoneDao phoneDao = new PhoneDaoHibernate(sessionManager);
        DBServicePhone dbServicePhone = new DbServicePhoneImpl(phoneDao);

        User user = new User("Ivan");
        user.setAddress(new Address("3-9 Kingdom", user));
        user.addPhone(new Phone("+7-999-1234567890", user));
        user.addPhone(new Phone("+7-000-0987654321", user));

        logger.info("========================");
        long id = dbServiceUser.saveUser(user);
        logger.info("========================");
        logger.info("persisted user:{}", user);

        Optional<User> optionalUser = dbServiceUser.getUser(id);
        logger.info("loaded user: {}", optionalUser.orElse(null));

        long addrId = optionalUser.get().getAddress().getId();
        Optional<Address> optionalAddress = dbServiceAddress.getAddress(addrId);
        logger.info("address of user: {}", optionalAddress.orElse(null));

        dbServiceUser.deleteUser(optionalUser.get());   // address & phones where deleted cascade

        // check if the address still exists
        optionalAddress = dbServiceAddress.getAddress(addrId);
        logger.info("address after delete user: {}", optionalAddress.orElse(null));
    }
}

