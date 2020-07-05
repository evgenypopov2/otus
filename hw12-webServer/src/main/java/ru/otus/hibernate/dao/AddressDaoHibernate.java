package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AddressDao;
import ru.otus.core.dao.AddressDaoException;
import ru.otus.core.model.Address;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class AddressDaoHibernate implements AddressDao {
    private static Logger logger = LoggerFactory.getLogger(AddressDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public AddressDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<Address> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(Address.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insertAddress(Address address) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(address);
            hibernateSession.flush();
            return address.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }

    @Override
    public void updateAddress(Address address) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(address);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(Address address) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (address.getId() > 0) {
                hibernateSession.merge(address);
            } else {
                hibernateSession.persist(address);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
