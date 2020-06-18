package ru.otus.jdbc.mapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;
    private final EntityClassMetaData<T> entityClassMetaData;
    private final EntitySQLMetaData<T> entitySQLMetaData;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor,
                          EntityClassMetaDataImpl<T> entityClassMetaData, EntitySQLMetaDataImpl<T> entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entityClassMetaData = entityClassMetaData;
        this.entitySQLMetaData = entitySQLMetaData;
        this.sessionManager = sessionManager;
    }

    @Override
    public void insert(T objectData) {
        try {
            dbExecutor.executeInsert(getConnection(), entitySQLMetaData.getInsertSql(), getParamsList(objectData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(T objectData) {
        try {
            dbExecutor.executeUpdate(getConnection(), entitySQLMetaData.getUpdateSql(), getParamsList(objectData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        Long id = null;
        Field idField = entityClassMetaData.getIdField();
        idField.setAccessible(true);
        try {
            id = (Long) idField.get(objectData);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        if (id != null) {
            Object obj = findById(id);
            if (obj == null) {
                insert(objectData);
            } else {
                update(objectData);
            }
        }
    }

    @Override
    public T findById(long id) {
        try {
            return dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(), id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                Object obj = entityClassMetaData.getConstructor().newInstance();
                                entityClassMetaData.getAllFields().forEach(field -> {
                                    field.setAccessible(true);
                                    try {
                                        field.set(obj, rs.getObject(field.getName()));
                                    } catch(Exception e) {
                                        logger.error(e.getMessage(), e);
                                    }
                                });
                                return (T) obj;
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    }
            ).orElse(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    private List<Object> getParamsList(T objectData) {
        List<Object> params = new ArrayList<>();
        entityClassMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            try {
                params.add(field.get(objectData));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new UserDaoException(e);
            }
        });
        return params;
    }
}
