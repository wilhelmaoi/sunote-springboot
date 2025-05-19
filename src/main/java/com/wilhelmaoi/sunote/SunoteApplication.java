package com.wilhelmaoi.sunote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.wilhelmaoi.sunote.config.OssProperties;

@SpringBootApplication
@EnableConfigurationProperties({OssProperties.class})
public class SunoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunoteApplication.class, args);
    }

}
