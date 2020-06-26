package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.PhoneDao;
import ru.otus.core.dao.PhoneDaoException;
import ru.otus.core.model.Phone;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class PhoneDaoHibernate implements PhoneDao {
    private static Logger logger = LoggerFactory.getLogger(PhoneDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public PhoneDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<Phone> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(Phone.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insertPhone(Phone phone) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(phone);
            hibernateSession.flush();
            return phone.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhoneDaoException(e);
        }
    }

    @Override
    public void updatePhone(Phone phone) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(phone);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhoneDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(Phone phone) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (phone.getId() > 0) {
                hibernateSession.merge(phone);
            } else {
                hibernateSession.persist(phone);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhoneDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
