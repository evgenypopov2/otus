package ru.otus.jdbc.mapper;

import ru.otus.core.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final List<Field> allFields = new ArrayList<>();
    private final List<Field> fieldsWithoutId = new ArrayList<>();
    private Field idField;

    public EntityClassMetaDataImpl(Class<T> clazz) throws NoSuchMethodException {

        this.clazz = clazz;
        constructor = clazz.getConstructor();

        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isTransient(field.getModifiers()) // not transient & not const
                    && !(Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))) {

                allFields.add(field);

                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                } else {
                    fieldsWithoutId.add(field);
                }
            }
        }
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
