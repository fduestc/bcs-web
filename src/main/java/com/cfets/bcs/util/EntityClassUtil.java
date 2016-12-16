package com.cfets.bcs.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 获取对象的实际类型
 * 
 * @author XWQ
 *
 */
public class EntityClassUtil {
  @SuppressWarnings("unchecked")
  private static <T> Class<T> getEntityClass(Class<T> entity, int index) {

    Type genType = entity.getGenericSuperclass();

    if (!(genType instanceof ParameterizedType)) {
      return (Class<T>) Object.class;
    }

    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

    if (index >= params.length || index < 0) {
      return (Class<T>) Object.class;
    }

    if (!(params[index] instanceof Class)) {
      return (Class<T>) Object.class;
    }

    return (Class<T>) params[index];
  }

  public static <T> Class<T> getEntityClass(Class<T> entity) {
    return (Class<T>) getEntityClass(entity, 0);
  }

}
