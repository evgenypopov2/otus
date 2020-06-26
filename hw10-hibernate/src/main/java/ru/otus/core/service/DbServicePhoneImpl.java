package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.PhoneDao;
import ru.otus.core.model.Phone;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServicePhoneImpl implements DBServicePhone {
    private static Logger logger = LoggerFactory.getLogger(DbServicePhoneImpl.class);

    private final PhoneDao phoneDao;

    public DbServicePhoneImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public long savePhone(Phone phone) {
        try (SessionManager sessionManager = phoneDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                phoneDao.insertOrUpdate(phone);
                long phoneId = phone.getId();
                sessionManager.commitSession();

                logger.info("created phone: {}", phoneId);
                return phoneId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<Phone> getPhone(long id) {
        try (SessionManager sessionManager = phoneDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Phone> phoneOptional = phoneDao.findById(id);

                logger.info("phone: {}", phoneOptional.orElse(null));
                return phoneOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
