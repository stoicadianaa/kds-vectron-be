package com.dianastoica.kdsvectron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class KdsVectronApplication {

    public static void main(String[] args) {
        SpringApplication.run(KdsVectronApplication.class, args);
    }

}
