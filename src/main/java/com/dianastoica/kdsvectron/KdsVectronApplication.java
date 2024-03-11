package com.dianastoica.kdsvectron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.TimeZone;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class KdsVectronApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Bucharest/Europe"));

        SpringApplication.run(KdsVectronApplication.class, args);
    }

}
