package com.dianastoica.kdsvectron.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ReadingConverter
public class StringToDateConverter implements Converter<String, Date> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date convert(String source) {
        try {
            return formatter.parse(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Could not parse date: " + source, e);
        }
    }
}
