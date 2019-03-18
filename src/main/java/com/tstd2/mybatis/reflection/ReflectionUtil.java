package com.tstd2.mybatis.reflection;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static <E> void setPropToBean(E entity, String fieldName, Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field f = entity.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(entity, obj);
    }

}
