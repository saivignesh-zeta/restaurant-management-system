package com.zeta.miniproject2.Restaurant.Management.System.Util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;

public class EntityUtil {
    public static <T> T copyNonNullProperties(T source, T target) {
        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);

        for (PropertyDescriptor propertyDescriptor : sourceWrapper.getPropertyDescriptors()) {
            String propertyName = propertyDescriptor.getName();
            if ("class".equals(propertyName) || propertyName.toLowerCase().endsWith("id")) {
                continue;
            }
            if (sourceWrapper.getPropertyValue(propertyName) != null && propertyDescriptor.getWriteMethod() != null) {
                Object value = sourceWrapper.getPropertyValue(propertyName);
                targetWrapper.setPropertyValue(propertyName, value);
            }
        }
        return target;
    }

}
