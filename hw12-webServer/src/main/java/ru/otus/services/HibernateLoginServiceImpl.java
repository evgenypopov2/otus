package ru.otus.services;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.util.security.Password;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.Optional;

public class HibernateLoginServiceImpl extends AbstractLoginService {

    private final DBServiceUser dbServiceUser;

    public HibernateLoginServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected String[] loadRoleInfo(UserPrincipal userPrincipal) {
        return new String[] {"user"};
    }

    @Override
    protected UserPrincipal loadUserInfo(String login) {
        System.out.println(String.format("HibernateLoginService#loadUserInfo(%s)", login));
        Optional<User> dbUser = dbServiceUser.findUserByLogin(login);
        return dbUser.map(u -> new UserPrincipal(u.getLogin(), new Password(u.getPassword()))).orElse(null);
    }
}
