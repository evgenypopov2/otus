package ru.otus;

import org.hibernate.SessionFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

public class DBServiceUserInit {

    public static DBServiceUser dbServiceUserInit() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, Address.class, Phone.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        dbServiceUser.saveUser(new User("Administrator", "admin", "admin"));
        dbServiceUser.saveUser(new User("Крис Гир", "user1", "11111"));
        dbServiceUser.saveUser(new User("Ая Кэш", "user2", "11111"));
        dbServiceUser.saveUser(new User("Десмин Боргес", "user3", "11111"));
        dbServiceUser.saveUser(new User("Кетер Донохью", "user4", "11111"));
        dbServiceUser.saveUser(new User("Стивен Шнайдер", "user5", "11111"));
        dbServiceUser.saveUser(new User("Джанет Вэрни", "user6", "11111"));
        dbServiceUser.saveUser(new User("Брэндон Смит", "user7", "11111"));
        return dbServiceUser;
    }
}
