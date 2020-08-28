package ru.otus.core.service;

import org.springframework.stereotype.Service;
import ru.otus.core.model.Address;
import ru.otus.core.model.User;

@Service
public class DataLoadServiceImpl implements DataLoadService {

    private final DBServiceUser dbServiceUser;

    public DataLoadServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public void loadData() {
        dbServiceUser.saveUser(newUser("Крис Гир", "user1", "11111", "Kris Girs' address"));
        dbServiceUser.saveUser(newUser("Ая Кэш", "user2", "22222", "Aya Cache street, 45"));
        dbServiceUser.saveUser(newUser("Десмин Боргес", "user3", "33333", "Borhes avenue, 242"));
        dbServiceUser.saveUser(newUser("Кетер Донохью", "user4", "44444", "Without address"));
        dbServiceUser.saveUser(newUser("Стивен Шнайдер", "user5", "55555", "Hello World"));
        dbServiceUser.saveUser(newUser("Джанет Вэрни", "user6", "66666", "White House"));
        dbServiceUser.saveUser(newUser("Брэндон Смит", "user7", "77777", "Black Smithy, 6"));
    }

    private User newUser(String name, String login, String password, String address) {
        User user = new User(name, login, password);
        user.setAddress(new Address(address, user));
        return user;
    }
}
