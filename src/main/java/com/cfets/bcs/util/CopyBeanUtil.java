package com.cfets.bcs.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.BeanUtils;

public class CopyBeanUtil {
  /**
   * 拷贝属性，并将基础类型装换为String类型
   * 
   * @param source
   * @param target
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws SecurityException
   * @throws NoSuchFieldException
   */
  public static void copyPropertiesAndConvertPrimitive(Object source, Object target) throws Exception {
    BeanUtils.copyProperties(source, target);

    Class<?> clazz = source.getClass();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      Class<?> fieldClazz = field.getType();

      // 避免获取到static的域
      if (Modifier.isFinal(field.getModifiers())) {
        continue;
      }

      // 基础类型转换为String
      if (fieldClazz.isPrimitive()) {
        field.setAccessible(true);
        Object value = field.get(source);

        Field targetField = target.getClass().getDeclaredField(field.getName());
        targetField.setAccessible(true);
        if (value != null) {
          targetField.set(target, String.valueOf(value));
        }

        continue;
      }

      // Date转换为String
      if (fieldClazz.isAssignableFrom(Date.class)) {
        field.setAccessible(true);
        Object value = field.get(source);

        Field targetField = target.getClass().getDeclaredField(field.getName());
        targetField.setAccessible(true);
        if (value != null) {
          targetField.set(target, DateUtil.getDateTimeString((Date) value));
        }

        continue;
      }

      // Timestamp转换为String
      if (fieldClazz.isAssignableFrom(Timestamp.class)) {
        field.setAccessible(true);
        Object value = field.get(source);

        Field targetField = target.getClass().getDeclaredField(field.getName());
        targetField.setAccessible(true);
        if (value != null) {
          // 时间到日期级别即可，不需要时分秒
          targetField.set(target, DateUtil.getDateString((Timestamp) value));
        }

        continue;
      }
    }
  }
}
