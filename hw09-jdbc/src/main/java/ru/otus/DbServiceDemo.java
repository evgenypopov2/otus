package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceAccountImpl;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.dao.AccountDaoJdbcMapper;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();

        demo.createTables(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);

        // user jdbc manipulations
        DbExecutorImpl<User> dbExecutorUser = new DbExecutorImpl<>();
        var userDao = new UserDaoJdbc(sessionManager, dbExecutorUser);
        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.save(new User(1, "dbServiceUser", 50));
        Optional<User> user = dbServiceUser.get(id);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        // account jdbc manipulations
        DbExecutorImpl<Account> dbExecutorAccount = new DbExecutorImpl<>();
        var accountDao = new AccountDaoJdbc(sessionManager, dbExecutorAccount);
        var dbServiceAccount = new DbServiceAccountImpl(accountDao);
        id = dbServiceAccount.save(new Account(1, "Account type", 8765.34));
        Optional<Account> account = dbServiceAccount.get(id);
        account.ifPresentOrElse(
                crAccount -> logger.info("created account, type: {}, rest: {}", crAccount.getType(), crAccount.getRest()),
                () -> logger.info("account was not created")
        );

        // user jdbc mapper manipulations
        UserDaoJdbcMapper userDaoJdbcMapper = new UserDaoJdbcMapper(sessionManager, dbExecutorUser);
        dbServiceUser = new DbServiceUserImpl(userDaoJdbcMapper);
        id = dbServiceUser.save(new User(2, "User by jdbc mapper", 55));
        user = dbServiceUser.get(id);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name: {}, age: {}", crUser.getName(), crUser.getAge()),
                () -> logger.info("user was not created")
        );

        // account jdbc mapper manipulations
        AccountDaoJdbcMapper accountDaoJdbcMapper = new AccountDaoJdbcMapper(sessionManager, dbExecutorAccount);
        dbServiceAccount = new DbServiceAccountImpl(accountDaoJdbcMapper);
        id = dbServiceAccount.save(new Account(2, "AccountType by jdbc mapper", 985694.87354));
        account = dbServiceAccount.get(id);
        account.ifPresentOrElse(
                crAccount -> logger.info("created account, type: {}, rest: {}", crAccount.getType(), crAccount.getRest()),
                () -> logger.info("account was not created")
        );
    }

    private void createTables(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pstUser = connection.prepareStatement("create table user(id bigint auto_increment, name varchar(50), age int)");
             var pstAccount = connection.prepareStatement("create table account(no bigint auto_increment, type varchar(50), rest double precision)")) {
            pstUser.executeUpdate();
            pstAccount.executeUpdate();
        }
        System.out.println("table created");
    }
}
