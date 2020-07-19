package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.core.dao.AddressDao;
import ru.otus.core.model.Address;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

@Service
public class DbServiceAddressImpl implements DBServiceAddress {
    private static Logger logger = LoggerFactory.getLogger(DbServiceAddressImpl.class);

    private final AddressDao addressDao;

    public DbServiceAddressImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public long saveAddress(Address address) {
        try (SessionManager sessionManager = addressDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                addressDao.insertOrUpdate(address);
                long addressId = address.getId();
                sessionManager.commitSession();

                logger.info("created address: {}", addressId);
                return addressId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<Address> getAddress(long id) {
        try (SessionManager sessionManager = addressDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Address> addressOptional = addressDao.findById(id);

                logger.info("address: {}", addressOptional.orElse(null));
                return addressOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
