package ru.otus.jdbc.mapper;

public interface EntitySQLMetaData<T> {
    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSql();

    String getUpdateSql();
}
