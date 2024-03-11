package com.dianastoica.kdsvectron.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class AppConfig {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Bucharest"));
    }
}