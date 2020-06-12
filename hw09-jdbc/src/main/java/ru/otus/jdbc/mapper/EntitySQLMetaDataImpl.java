package ru.otus.jdbc.mapper;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private static final String START_SELECT = "select ";
    private static final String START_INSERT = "insert into ";
    private static final String START_UPDATE = "update ";

    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(Class<T> clazz) throws NoSuchMethodException {
        EntityClassMetaData<T> entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);

        StringBuilder sbQuery = new StringBuilder(START_SELECT);
        entityClassMetaData.getAllFields().forEach(field -> {
            sbQuery.append(sbQuery.length() > START_SELECT.length() ? "," : "").append(field.getName());
        });
        sbQuery.append(" from ").append(entityClassMetaData.getName());
        selectAllSql = sbQuery.toString();

        sbQuery.append(" where ").append(entityClassMetaData.getIdField().getName()).append("=?");
        selectByIdSql = sbQuery.toString();

        insertSql = createInsertSql(entityClassMetaData);
        updateSql = createUpdateSql(entityClassMetaData);
    }

    private String createInsertSql(EntityClassMetaData<T> entityClassMetaData) {
        return createSql(entityClassMetaData, START_INSERT, "(", ") values (");
    }

    private String createUpdateSql(EntityClassMetaData<T> entityClassMetaData) {
        return createSql(entityClassMetaData, START_UPDATE, " set (", ")=(");
    }

    private String createSql(EntityClassMetaData<T> entityClassMetaData, String startQuery, String queryFragment, String querySetParameters) {
        StringBuilder sbQuery = new StringBuilder(startQuery).append(entityClassMetaData.getName()).append(queryFragment);
        int startLength = sbQuery.length();
        StringBuilder sbParameters = new StringBuilder();
        entityClassMetaData.getFieldsWithoutId().forEach(field -> {
            sbQuery.append(sbQuery.length() > startLength ? "," : "").append(field.getName());
            sbParameters.append(sbParameters.length() > 0 ? ",?" : "?");
        });
        sbQuery.append(querySetParameters).append(sbParameters.toString()).append(")");
        return sbQuery.toString();
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }
}
