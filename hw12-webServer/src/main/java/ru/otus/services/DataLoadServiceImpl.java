package ru.otus.services;

import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

public class DataLoadServiceImpl implements DataLoadService {

    private final DBServiceUser dbServiceUser;

    public DataLoadServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public void loadData() {
        dbServiceUser.saveUser(new User("Administrator", "admin", "admin"));
        dbServiceUser.saveUser(new User("Крис Гир", "user1", "11111"));
        dbServiceUser.saveUser(new User("Ая Кэш", "user2", "11111"));
        dbServiceUser.saveUser(new User("Десмин Боргес", "user3", "11111"));
        dbServiceUser.saveUser(new User("Кетер Донохью", "user4", "11111"));
        dbServiceUser.saveUser(new User("Стивен Шнайдер", "user5", "11111"));
        dbServiceUser.saveUser(new User("Джанет Вэрни", "user6", "11111"));
        dbServiceUser.saveUser(new User("Брэндон Смит", "user7", "11111"));
    }
}
