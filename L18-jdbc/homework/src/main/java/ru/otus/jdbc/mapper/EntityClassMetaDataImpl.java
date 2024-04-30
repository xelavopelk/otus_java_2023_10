package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import ru.otus.crm.model.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> cl;
    private final Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> cls) throws NoSuchMethodException {
        cl = cls;
        constructor = cl.getConstructor();
    }

    @Override
    public String getName() {
        var res = cl.getName()
                .substring(
                        Math.min(cl.getName().lastIndexOf('.') + 1, cl.getName().length() - 1));
        return res;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        var fields = cl.getDeclaredFields();
        var res = Arrays.stream(fields)
                .filter(f -> f.getAnnotation(Id.class) != null)
                .toList();
        if (res.isEmpty()) {
            throw new ClassCastException(String.format("no id found in class=%s", cl.getName()));
        } else if (res.size() > 1) {
            throw new ClassCastException(String.format("too may id in class=%s", cl.getName()));
        } else {
            return res.getFirst();
        }
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(cl.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(cl.getDeclaredFields())
                .filter(f -> f.getAnnotation(Id.class) == null)
                .toList();
    }
}
