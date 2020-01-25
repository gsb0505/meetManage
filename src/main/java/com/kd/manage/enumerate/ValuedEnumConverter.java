package com.kd.manage.enumerate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author: latham
 * @Date: 2019/12/29 22:31
 **/
public class ValuedEnumConverter implements ConverterFactory<String,PathTypeEnum> {


    @Override
    public <T extends PathTypeEnum> Converter<String, T> getConverter(Class<T> aClass) {
        return new StringToEnum(aClass);
    }

    private class StringToEnum<T extends Enum> implements Converter<String,T>{

        private Class<T> enumType;

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String s) {
            if(s.length() == 0) {
                return null;
            }

            return (T)enumType.getEnumConstants()[Integer.parseInt(s.trim())];
        }
    }
}
