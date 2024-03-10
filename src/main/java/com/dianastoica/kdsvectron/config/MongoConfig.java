package com.dianastoica.kdsvectron.config;

import com.dianastoica.kdsvectron.utils.StringToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(new StringToDateConverter()));
    }
}
